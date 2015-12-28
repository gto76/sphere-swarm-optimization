package sso.graphics;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;

import sso.ParticleContainer;

import java.awt.geom.*;
import java.beans.PropertyVetoException;
import java.util.HashSet;
import java.util.LinkedList;


/**
 * @(#)Fcb.java
 *
 * JFC Sample application
 *
 * @author Jure Sorn
 * @version 1.00 08/04/03
 */

public class FcbFrame extends JFrame {

	ParticleContainer pc;

    double sp = 800; //space - x, y
    static int SIZE = 200; //prej 100
    static int center = 200; // kordinate centra //prej oboje 300
    MyInternalFrame frame;
    static Timer timer;
    int REFRESH_RATE = 5; // (ms) refresh rate //200
    boolean INSTANT_EXECUTION = true;
    int ZM = 500; //ZOOM
    static boolean RISI_POVEZAVE = false;
    
    LinkedList<String> razredi;
    LinkedList<Color> barve = new LinkedList<Color>();
    
	final  Color red = Color.red;
	final  Color green = Color.green;
	final  Color blue = Color.blue;
	final  Color orange = Color.orange;
	final  Color pink = Color.pink;
	final  Color cyan = Color.cyan;
	final  Color darkGray = Color.darkGray;
	final  Color magenta = Color.magenta;

	boolean stopFlag;

    JDesktopPane desktop;
    /**
     * The constructor.
     */
     public FcbFrame(ParticleContainer pc) {
    	 this.pc = pc;
    	 initEverything();
     }

     public FcbFrame(ParticleContainer pc, int refreshRate) {
    	 this.pc = pc;
    	 this.REFRESH_RATE = refreshRate;
    	 initEverything();
     }
     
     
     private void initEverything() {
    	 
    	//razredi
    /*	HashSet<String> raz = new HashSet<String>();
    	for ( Particle primerek : Particle.particles ) {
    		raz.add(primerek.razred);
    	}
    	razredi = new LinkedList<String>(raz);
    	*/
    	//barve
    	barve.add(Color.red);
    	barve.add(Color.green);
    	barve.add(Color.blue);
    	barve.add(Color.orange);
    	barve.add(Color.pink);
    	barve.add(Color.cyan);
    	barve.add(Color.darkGray);
    	barve.add(Color.magenta);
    	barve.add(Color.black);
    	barve.add(Color.lightGray);
    	
    	

        JMenuBar menuBar = new JMenuBar();
        JMenu menuFile = new JMenu();
        //JMenuItem menuFile = new JMenuItem();
        JMenuItem menuFileExit = new JMenuItem();
        JMenuItem menuFilePrint = new JMenuItem();
        JMenuItem menuFileResitev = new JMenuItem();
        //JMenuItem menuFileOpen = new JMenuItem();
        //JMenuItem menuFileMove = new JMenuItem();
        JMenuItem menuFileAnimacija = new JMenuItem();
        JMenuItem menuFileStop = new JMenuItem();


        menuFile.setText("File");
        //menuFile.setText("");
        menuFileExit.setText("Exit");
        menuFilePrint.setText("New Frame");
        menuFileResitev.setText("Izpisi Resitev");
        //menuFileOpen.setText("Open...");
        //menuFileMove.setText("Move");
        menuFileAnimacija.setText("Animacija");
        menuFileStop.setText("Stop");

        /*inicializacije*********************/

        //nastavitve za internal frame
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        /*
        setBounds(inset + 30, inset,
                  screenSize.width  - inset*2,
                  screenSize.height - inset*2);
        */
    
        setBounds(inset, inset,
                  screenSize.width,
                  screenSize.height);


        
        

        desktop = new JDesktopPane();
        //createFrame();
        setContentPane(desktop);




        //nastavitev timerja
        timer = new Timer(REFRESH_RATE, null);
		timer.setInitialDelay(REFRESH_RATE);

		if ( INSTANT_EXECUTION ) {
		    if ( frame == null ) {
                BufferedImage img = createImage();
				frame = createFrame(img);

            }

			//da se zacne animacija
			timer.start();

		}


        /*action listenerji*********************/

        //Se sprozi vsako desetinko po tem ko
        //zazenemo TIMER
        timer.addActionListener
        (
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {

				    putImageInFrame(frame, createImage());

                }
            }
        );

        // Add action listener.for the menu button
        menuFileExit.addActionListener
        (
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    FcbFrame.this.windowClosed();
                }
            }
        );

        //FILE PRINT
        menuFilePrint.addActionListener
        (
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	BufferedImage img = createImage();
					frame = createFrame(img);
                }
            }
        );

        //FILE ANIMACIJA
        menuFileAnimacija.addActionListener
        (
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                if ( frame == null ) {
                    BufferedImage img = createImage();
					frame = createFrame(img);

                }

					//da se zacne animacija
					timer.start();

			    }
            }
        );

        //FILE STOP
        menuFileStop.addActionListener
        (
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {

					timer.stop();
					//System.out.println("was stopped");

			    }
            }
        );



        /*****MENU******/

        //menuFile.add(menuFile);
        menuFile.add(menuFilePrint);
        menuFile.add(menuFileAnimacija);
        menuFile.add(menuFileStop);
        //menuFile.add(menuFileResitev);
        menuFile.add(menuFileExit);

        menuBar.add(menuFile);


        setTitle("Poroka");
        setJMenuBar(menuBar);
        setSize(new Dimension(400, 400));

        // Add window listener.
        this.addWindowListener
        (
            new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    FcbFrame.this.windowClosed();
                }
            }
        );
    }

    /**
     * Metode
     */


    //Create a new internal frame with image.
    protected MyInternalFrame createFrame(BufferedImage img) {
    	MyInternalFrame frame = new MyInternalFrame();
        frame.setSize(600,600);

        putImageInFrame(frame, img);

        frame.setVisible(true);
        desktop.add(frame);
        try {
            frame.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {}

        try {
            frame.setMaximum(true);
        } catch (PropertyVetoException e) {
            e.printStackTrace();  // Just print the stack trace
        }

        return frame;
    }

    private void putImageInFrame(MyInternalFrame frame, BufferedImage img) {
    	ImageIcon icon = new ImageIcon((Image)img, "description");
        JLabel label = new JLabel(icon);
        JScrollPane scrollPane = new JScrollPane(label);
        frame.setContentPane(scrollPane);
        //frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
    }


    private BufferedImage createImage() {

		final  Color black = Color.black;
		final  Color red = Color.red;
	    	BufferedImage img =
		new BufferedImage((int) sp, (int) sp, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2 = img.createGraphics();
		g2.setPaint(black);

		for ( Integer i = 0; i < pc.particles.size(); i++ ) {
	    	//System.out.print(folk[i].toString());

	    	/*Peer[] enemies = Poroka.folk[i].getEnemies();
	    	Peer[] doubleEnemies = Poroka.folk[i].getDoubleEnemies();
	    	g2.setPaint(black);*/
	    	// povezave:
	    /*	if ( RISI_POVEZAVE ) {
		    	for ( int j = 0; j < Poroka.folk[i].numberOfEnemies; j++) {
		    		g2.draw(new Line2D.Double(Poroka.folk[i].x * sp/ZM + (sp/2), Poroka.folk[i].y * sp/ZM + (sp/2),
		    								enemies[j].x * sp/ZM + (sp/2), enemies[j].y * sp/ZM + (sp/2)));
		    	}

				for ( int j = 0; j < Poroka.folk[i].numberOfDoubleEnemies; j++) {
		    		g2.draw(new Line2D.Double(Poroka.folk[i].x * sp/ZM + (sp/2), Poroka.folk[i].y * sp/ZM + (sp/2),
		    								doubleEnemies[j].x * sp/ZM + (sp/2), doubleEnemies[j].y * sp/ZM + (sp/2)));
		    		g2.draw(new Line2D.Double(Poroka.folk[i].x * sp/ZM + (sp/2) + 1, Poroka.folk[i].y * sp/ZM + (sp/2) + 1,
		    								doubleEnemies[j].x * sp/ZM + (sp/2) + 1, doubleEnemies[j].y * sp/ZM + (sp/2) + 1));
		    	}
	    	}*/
			
			//String razred = Iris.prim.get(i).razred;
			//int index = razredi.indexOf(razred);
			Color color = barve.get( 1 );			
	    	g2.setPaint(color);
			//g2.setPaint(red);
	    	//g2.draw(new Arc2D.Double( (int) (SphereClustering.koordinate[0][i] * SIZE) + center, (int) (SphereClustering.koordinate[1][i] * SIZE) + center,
			//		6.0, 6.0, 0, 360, Arc2D.OPEN) );
	    	g2.draw(new Arc2D.Double( (int) (pc.particles.get(i).po.get(0) * SIZE) + center, (int) (pc.particles.get(i).po.get(1) * SIZE) + center,
					6.0, 6.0, 0, 360, Arc2D.OPEN) );

	    	//Font font = new Font("Dialog", Font.PLAIN, 12);
	    	Integer ii = i+1;
	    	//g2.drawString(ii.toString(), (int) (SphereClustering.koordinate[0][i] * SIZE) + center,
			//		(int) (SphereClustering.koordinate[1][i] * SIZE) + center );
	    	g2.drawString(ii.toString(), (int) (pc.particles.get(i).po.get(0) * SIZE) + center,
					(int) (pc.particles.get(i).po.get(1) * SIZE) + center );






	    }
		
		double koorKrizca = sp/2 - (400 - center);
		
	    g2.draw(new Arc2D.Double(koorKrizca - ((sp/ZM)) , koorKrizca - ((sp/ZM)),
	    								2*sp/ZM , 2*sp/ZM , 0, 360, Arc2D.OPEN) );
	    g2.setPaint(black);
	    g2.draw(new Arc2D.Double((koorKrizca)-2,(koorKrizca)-2, 4, 4 , 0, 360, Arc2D.OPEN) );
	    g2.draw(new Arc2D.Double((koorKrizca)-1,(koorKrizca)-1, 2, 2 , 0, 360, Arc2D.OPEN) );

	    return img;

	}






    /**
     * Shutdown procedure when run as an application.
     */
    protected void windowClosed() {
        // Exit application.
        System.exit(0);
    }
}

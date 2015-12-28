package sso.graphics;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;

import sso.ParticleContainer;
import sso.gauss.GParticleContainer;

import java.awt.geom.*;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
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

public class GFcbFrame extends JFrame {

	GParticleContainer pc;
	
	double radius = 1.0;

    double sp = 800; //space - x, y
    static int SIZE = 300; //prej 100
    static int center = 300; // kordinate centra
    private MyInternalFrame frame;
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
     public GFcbFrame(GParticleContainer pc) {
    	 this.pc = pc;
    	 initEverything();
     }

     public GFcbFrame(GParticleContainer pc, int refreshRate) {
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



/*
        //nastavitev timerja
        timer = new Timer(REFRESH_RATE, null);
		timer.setInitialDelay(REFRESH_RATE);
*/
		if ( INSTANT_EXECUTION ) {
		    if ( getFrame() == null ) {
                BufferedImage img = createImage();
				setFrame(createFrame(img));

            }

			//da se zacne animacija
			//timer.start();

		}


        /*action listenerji*********************/

        //Se sprozi vsako desetinko po tem ko
        //zazenemo TIMER
		/*
        timer.addActionListener
        (
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {

				    putImageInFrame(frame, createImage());

                }
            }
        );
*/

        // Add action listener.for the menu button
        menuFileExit.addActionListener
        (
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    GFcbFrame.this.windowClosed();
                }
            }
        );

        //FILE PRINT
        menuFilePrint.addActionListener
        (
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	BufferedImage img = createImage();
					setFrame(createFrame(img));
                }
            }
        );

        //FILE ANIMACIJA
        menuFileAnimacija.addActionListener
        (
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                if ( getFrame() == null ) {
                    BufferedImage img = createImage();
					setFrame(createFrame(img));

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
                    GFcbFrame.this.windowClosed();
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

    public void putImageInFrame(MyInternalFrame frame, BufferedImage img) {
    	ImageIcon icon = new ImageIcon((Image)img, "description");
        JLabel label = new JLabel(icon);
        JScrollPane scrollPane = new JScrollPane(label);
        frame.setContentPane(scrollPane);
        //frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
    }
	
    
    public enum Orientacija {
    	HORIZONTAL, VERTICAL, DEPTH;
    }

    
		    //#############//
		    //## RISANKE ##//
		    //#############//

    public BufferedImage createImage() {
    	BufferedImage img =
    		new BufferedImage((int) sp, (int) sp, BufferedImage.TYPE_INT_ARGB);
    	Graphics2D g2 = img.createGraphics();
    	
    	
    	double[][] ar1 = create3DHeatMap(Orientacija.VERTICAL, 5);
    	//double[][] ar2 = create3DHeatMap(Orientacija.HORIZONTAL);
    	//double[][] ar3 = create3DHeatMap(Orientacija.DEPTH);
    	addToBufferedImageFromArray(g2, ar1, 0, 0, 1.0);
    	//addToBufferedImageFromArray(g2, ar2, 320, 0, 1.0);
    	//addToBufferedImageFromArray(g2, ar3, 0, 340, 1.0);
    	addPointsTo3dHeatMap(g2, 0, 0, 5, Orientacija.VERTICAL);
    	
    	createProjectionOfPointsAbsolute(g2, 310, 0);
    	
    	//double[][] ar = create2DHeatMap(700);
    	//addToBufferedImageFromArray(g2, ar, 0, 0, 1.0);    	
    	
		return img;
    }
	

    private void createProjectionOfPointsAbsolute(Graphics2D g2, int x, int y) {
		//Frame
    	g2.setPaint(Color.black);
    	g2.fillRect(x, y, SIZE, SIZE);
    	/*
    	g2.drawLine(x, y, x+SIZE, y);
    	g2.drawLine(x, y, x, y+SIZE);
    	g2.drawLine(x+SIZE, y, x+SIZE, y+SIZE);
    	g2.drawLine(x, y+SIZE, x+SIZE, y+SIZE);
    	*/
    	g2.setPaint(Color.gray);
    	g2.fillArc(x, y, SIZE, SIZE, 0, 360);
    	//g2.drawArc(x, y, SIZE, SIZE, 0, 360);
    	
    	
    	//Particles
		for ( Integer i = 0; i < pc.particles.size(); i++ ) {
		//for ( Integer i = 0; i < pc.listOfPoints.size(); i++ ) {
			Color color = barve.get( 1 );			
	    	g2.setPaint(color);
	    	ArrayList<Double> tocka = pc.particles.get(i).oldPoint.getOriginalPosition();
	    	//ArrayList<Double> tocka = pc.listOfPoints.get(i).getOriginalPosition();
	    	preslikajVEnotsko(tocka);
	    	drawParticleAbsolute(g2, (int)(tocka.get(0)*SIZE)+x, 
	    								(int)(tocka.get(1)*SIZE)+y, 
	    								//pc.listOfPoints.get(i).getPointId()+"");
	    								(new Integer(i+1)).toString());
	    }
		//Global Best:
		if (pc.globalBestPoint.p != null) {
			ArrayList<Double> gb = pc.globalBestPoint.p.getOriginalPosition();
			Color color = barve.get( 2 );			
	    	g2.setPaint(color);
	    	preslikajVEnotsko(gb);
	    	drawParticleAbsolute(g2, (int)(gb.get(0)*SIZE)+x, (int)(gb.get(1)*SIZE)+y, "GB");
		}
		//drawCross(g2, x, y);
	}

    private void createProjectionOfPointsAbsoluteLast2Dim(Graphics2D g2, int x, int y) {
		//Particles
		for ( Integer i = 0; i < pc.particles.size(); i++ ) {
			Color color = barve.get( 1 );			
	    	g2.setPaint(color);
	    	ArrayList<Double> tocka = pc.particles.get(i).oldPoint.getOriginalPosition();
	    	int s = tocka.size();
	    	drawParticleAbsolute(g2, (int)(tocka.get(s-2)*SIZE)+x, 
	    								(int)(tocka.get(s-1)*SIZE)+y, 
	    							(new Integer(i+1)).toString());
	    }
		//Global Best:
		if (pc.globalBestPoint.p != null) {
			ArrayList<Double> gb = pc.globalBestPoint.p.getOriginalPosition();
			Color color = barve.get( 2 );			
	    	g2.setPaint(color);
	    	int s = gb.size();
	    	drawParticleAbsolute(g2, (int)(gb.get(s-2)*SIZE)+x, (int)(gb.get(s-1)*SIZE)+y, "GB");
		}
		drawCross(g2, x, y);
	}

    private void createProjectionOfPoints(Graphics2D g2, int x, int y) {
		//Particles
		for ( Integer i = 0; i < pc.particles.size(); i++ ) {
			Color color = barve.get( 1 );			
	    	g2.setPaint(color);
	    	ArrayList<Double> tocka = pc.particles.get(i).oldPoint.getOriginalPosition();
	    	drawParticle(g2, tocka.get(0), tocka.get(1), (new Integer(i+1)).toString());
	    }
		//Global Best:
		if (pc.globalBestPoint.p != null) {
			ArrayList<Double> gb = pc.globalBestPoint.p.getOriginalPosition();
			Color color = barve.get( 2 );			
	    	g2.setPaint(color);
	    	drawParticle(g2, gb.get(0), gb.get(1), "GB");
		}
		drawCross(g2, 0, 0);
	}
    

    public void addPointsTo3dHeatMap(Graphics2D g2, int x, int y, int noOfSlices,
    		Orientacija ori) {
    	//Particles
		for ( Integer i = 0; i < pc.particles.size(); i++ ) {
			g2.setPaint(Color.white);
	    	ArrayList<Double> tocka = pc.particles.get(i).oldPoint.getNormalisedPosition();
	    	drawPointTo3dHeatMap(g2, tocka, x, y, noOfSlices, ori, (new Integer(i+1)).toString());
	    }
		//Global Best:
		g2.setPaint(Color.black);
		ArrayList<Double> tocka = pc.getGlobalBest().p.getNormalisedPosition();
    	drawPointTo3dHeatMap(g2, tocka, x, y, noOfSlices, ori, "GB");
    
    }
    
    int gap = 13;
    
    public void drawPointTo3dHeatMap(Graphics2D g2, ArrayList<Double> tocka, int X, int Y, 
    		int noOfSlices,	Orientacija ori, String tag) {
    	preslikajVEnotsko(tocka);
    	
    	double x = tocka.get(0);
        double y = tocka.get(1);
        double z = tocka.get(2);
    	
    	int sp = SIZE / noOfSlices;
    	double f = (double)(noOfSlices-1)/noOfSlices;
    	
    	int j = (int)(sp + (x*f)*SIZE - y*sp);
    	int i = (int)((int)(z*noOfSlices) * sp + y*sp);   ; //Tle mora bit (int) obvezno kjer je!!
    	
    	drawParticleAbsolute(g2, j, i, tag);
    	
    }
    

    public double[][] create3DHeatMap(Orientacija ori, final int noOfSlices) {
		double[][] image = new double[SIZE/*+((noOfSlices-1)*gap)*/][SIZE];

		int noOfDim = pc.getNoOfDim();
		final int sp = SIZE/noOfSlices; //sirina preseka

		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				ArrayList<Double> loc = new ArrayList<Double>(noOfDim);
				double x;
				double y;
				double z;
				
				if ( ori.equals(Orientacija.VERTICAL) ) {
					//koordinate od 0 do 1:
					// j -> x:levo-desno; i -> y in z
					x = (double)(j - sp + (i%sp))/(SIZE-sp); //x-prva sp: levo-desno
					y = (double)(i%sp)/sp;					//y-druga sp: posevno
					z = (double)(i/sp)/noOfSlices;			//z-tretja sp: gor dol 
										//-> prvi z je 0, zadnji pa (noOfSlices-1)/noOfSlices
					
				}
				else if ( ori.equals(Orientacija.HORIZONTAL) ) {
					x = (double)(i%sp)/sp;
					y = (double)(j - sp + (i%sp))/(SIZE-sp);
					z = (double)(i/sp)/noOfSlices;
				}/*
				else if ( ori.equals(Orientacija.DEPTH) ) {
					x = (double)(i%sp)/sp;
					y = (double)(i/sp)/noOfSlices;
					z = (double)(j - sp + (i%sp))/(SIZE-sp);
				}*/
				else {
					x = (double)(j - sp + (i%sp))/(SIZE-sp);
					y = (double)(i/sp)/noOfSlices;	
					z = (double)(i%sp)/sp;
				}
				
				if ( isInRange(x,0.0,1.0) 
					&& isInRange(y,0.0,1.0)
					&& isInRange(z,0.0,1.0) ) {
					
					loc.add(x * 2.0 - 1.0);
					loc.add(y * 2.0 - 1.0);
					loc.add(z * 2.0 - 1.0);
								
					for (int k = 0; k < noOfDim - 2; k++) {
						//loc.add(0.0);
						//gre v ravnino v kateri je najboljsa tocka!!
						loc.add(pc.globalBestPoint.p.getNormalisedPosition()
								.get(k));
					}
					double fit = pc.getNormalFitnesOfLocation(loc, radius);
	
					image[i][j] = fit;
				}
				else {
					image[i][j] = Double.NaN;
				}			
			}
		}
		return image;
	}

    	
    /**
     * od -1 do 1 preslika v od 0 do 1
     * @param tocka
     */
    public static void preslikajVEnotsko(ArrayList<Double> tocka) {
    	for (int i = 0; i < tocka.size(); i++) {
			double element = (tocka.get(i)/2) + 0.5;
    		tocka.set(i, element);
		}
    }
    
    
    public double[][] create2DHeatMap(int size) {
    	double[][] image = new double[size][size];
		int noOfDim = pc.getNoOfDim();
    	
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				ArrayList<Double> loc = new ArrayList<Double>(noOfDim);
				loc.add(((double)i/size)*2.0 - 1.0);
				loc.add(((double)j/size)*2.0 - 1.0);
				for (int k = 0; k < noOfDim-2; k++) {
					//loc.add(0.0);
					//gre v ravnino v kateri je najboljsa tocka!!
					loc.add(pc.globalBestPoint.p.getNormalisedPosition().get(k));
				}
				double fit = pc.getNormalFitnesOfLocation(loc, radius);
				
				image[i][j] = fit;
			}
		}
		return image;
	}
    

    public void addToBufferedImageFromArray(Graphics2D g2, double[][] array, int x, int y,
    											double paleteWidth) {
    	
    	double avgMin = getExtrem(array,false);
    	double avgMax = getExtrem(array,true);
    	double avgRange = avgMax - avgMin;

    	for (int i = 0; i < array.length; i++) {
    		for (int j = 0; j < array[0].length; j++) {
    			Color color;
    			double brightness;
    			if ( Double.isNaN(array[i][j])) {
    				color = Color.black;
    			}
    			else {
    				brightness = (array[i][j] - avgMin) / avgRange;
    				color = Color.getHSBColor((float)(brightness*paleteWidth), (float)1.0, (float)1.0);
    			}
    			g2.setPaint(color);
    			g2.drawLine(j+x, i+y, j+x, i+y);
    		}
    	}

    	g2.setPaint(Color.black);
    	int yy = array.length+y;
    	g2.drawString("min particle:"+pc.getMinFitnes(), x, yy+310);
    	g2.drawString("max particle:"+pc.getMinFitnes(), x, yy+320);
    	g2.drawString("min map:"+avgMin, x, yy+330);
    	g2.drawString("max map:"+avgMax, x, yy+340);
    	
    }
    
			    //#############//
			    //## RISANKE ##//
			    //#############//


    

    public boolean isInRange(double num, double from, double to) {
    	if ( (num>=from) && (num<=to) ) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    
    private double getExtrem(double[][] img, boolean max) {
    	double ext;
    	if (max) ext = -Double.MAX_VALUE;
    	else ext = Double.MAX_VALUE;
    	
    	for (int i = 0; i < img.length; i++) {
			for (int j = 0; j < img[i].length; j++) {
				if (max) {
					if (img[i][j] > ext) {
						ext = img[i][j];
					}
				}
				else {
					if (img[i][j] < ext) {
						ext = img[i][j];
					}
				}
			}
		}
    	return ext;
    }


    private void drawCross(Graphics2D g2, double x, double y) {
    	g2.setPaint(Color.red);
	    g2.draw(new Arc2D.Double((int) (x * SIZE) + center - (sp/ZM), (int) (y * SIZE) + center- (sp/ZM),
	    								2*sp/ZM , 2*sp/ZM , 0, 360, Arc2D.OPEN) );
		g2.setPaint(Color.black);
	    g2.draw(new Arc2D.Double((int) (x * SIZE) + center-2,(int) (y * SIZE) + center-2, 4, 4 , 0, 360, Arc2D.OPEN) );
	    g2.draw(new Arc2D.Double((int) (x * SIZE) + center-1,(int) (y * SIZE) + center-1, 2, 2 , 0, 360, Arc2D.OPEN) );

    }
    	
    private void drawParticle(Graphics2D g2, double x, double y, String tag) {
    	// circle:
    	g2.draw(new Arc2D.Double( (int) (x * SIZE) + center, (int) (y * SIZE) + center,
				6.0, 6.0, 0, 360, Arc2D.OPEN) );
    	// tag:
    	g2.drawString(tag, (int) (x * SIZE) + center,
				(int) (y * SIZE) + center );
    }

    private void drawParticleAbsolute(Graphics2D g2, int x, int y, String tag) {
    	// circle:
    	int r = 3;
    	g2.draw(new Arc2D.Double( x-r, y-r,
				r*2, r*2, 0, 360, Arc2D.OPEN) );
    	// tag:
    	g2.drawString(tag, x-r, y-r);
    }

    /**
     * Shutdown procedure when run as an application.
     */
    protected void windowClosed() {
        // Exit application.
        System.exit(0);
    }

	public void setFrame(MyInternalFrame frame) {
		this.frame = frame;
	}

	public MyInternalFrame getFrame() {
		return frame;
	}
}

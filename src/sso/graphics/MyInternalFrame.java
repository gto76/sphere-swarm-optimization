package sso.graphics;
import javax.swing.JInternalFrame;
import java.awt.image.*;



class MyInternalFrame extends JInternalFrame {
	
	static int openFrameCount = 0;
	static final int xOffset = 30, yOffset = 30;
	BufferedImage img;

	public MyInternalFrame() {
	    super("Document #" + (++openFrameCount),
	          true, //resizable
	          true, //closable
	          true, //maximizable
	          true);//iconifiable
	    //...Create the GUI and put it in the window...
	    //...Then set the window size or call pack...
	    
	    //Set the window's location.
	    setLocation(xOffset*openFrameCount, yOffset*openFrameCount);
	   
	}
	
	public MyInternalFrame(BufferedImage imgImport) {
		super("Document #" + (++openFrameCount),
	          true, //resizable
	          true, //closable
	          true, //maximizable
	          true);//iconifiable
	    //...Create the GUI and put it in the window...
	    //...Then set the window size or call pack...
	    
	    img = imgImport;
	    
	    //Set the window's location.
	    setLocation(xOffset*openFrameCount, yOffset*openFrameCount);

	}
	
	public BufferedImage returnImg() {
		return img;
	}
	

	
}

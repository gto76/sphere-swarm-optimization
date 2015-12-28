package sso.io;

import java.util.ArrayList;

public class Razpon {

	public final double from;
	public final double to;
	
	public Razpon(double from, double to) {
		this.from = from;
		this.to = to;
	}
	
	
	static public ArrayList<Razpon> getRazponi(double from, double to,
						int noOfSprem) {
		
		ArrayList<Razpon> razponi = new ArrayList<Razpon>(noOfSprem);
		for (int i = 0; i < noOfSprem; i++) {
			Razpon raz = new Razpon(from, to);
			razponi.add(raz);
		}
	
		return razponi;		
	}

}

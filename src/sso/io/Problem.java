package sso.io;

import java.util.ArrayList;

import sso.functions.Function;

public class Problem {

	public final ArrayList<Razpon> razponi;
	public final int steviloSpremenljivk;
	public final boolean MAX; //true -> find maximum
	public final Function enacba;	
	
	public Problem(ArrayList<Razpon> razponi, boolean MAX, Function enacba) {	
		this.razponi = razponi;
		this.steviloSpremenljivk = razponi.size();
		this.MAX = MAX;
		this.enacba = enacba;			
	}

	@Override
	public String toString() {		
		String string = new String(steviloSpremenljivk + "\t" + MAX 
				+ "\t" + enacba);
		return string;
	}
	
}

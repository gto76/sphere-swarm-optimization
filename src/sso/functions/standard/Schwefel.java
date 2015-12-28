package sso.functions.standard;

import java.util.ArrayList;

import sso.functions.Function;
import sso.io.Razpon;

/**
 * Schwefel's function:
 * Global minimum -n 418.9829
 */

public class Schwefel extends Function {

	final int n = 5;
	
	public Schwefel() {
		super("Schwefel");
	}
	
	@Override
	public double calculate(ArrayList<Double> X) {
		//f8 
		double result = 0;
		for ( Double x : X ) {
			result -= x * Math.sin( Math.sqrt( Math.abs(x) ) );
		}
		return result;
	}

	@Override
	public ArrayList<Razpon> getDefaultRazponi() {
		return Razpon.getRazponi(-500, 500, n);
	}

	@Override
	public double getBestPossibleResult() {
		return -n*418.9829;
	}

}

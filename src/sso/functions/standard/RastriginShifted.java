package sso.functions.standard;

import java.util.ArrayList;

import sso.functions.Function;
import sso.io.Razpon;

/*
 * Rastrigin's finction
 */

public class RastriginShifted extends Function {

	final int n = 100;

	public RastriginShifted() {
		super("RastriginShifted");
	}
	
	@Override
	public double calculate(ArrayList<Double> X) {
		double result = 0;
		for ( Double x : X ) {
			result += (Math.pow(x,2) - 10*Math.cos( 2*Math.PI*(x) ) + 10);
		}
		return result - 330;
	}
	
	@Override
	public ArrayList<Razpon> getDefaultRazponi() {
		return Razpon.getRazponi(-5, 5, n);
	}

	@Override
	public double getBestPossibleResult() {
		return -330;
	}

}
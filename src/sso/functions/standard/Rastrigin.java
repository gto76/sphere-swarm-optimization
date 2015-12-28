package sso.functions.standard;

import java.util.ArrayList;

import sso.functions.Function;
import sso.io.Razpon;

/*
 * Rastrigin's finction
 */

public class Rastrigin extends Function {

	final int n = 5;

	public Rastrigin() {
		super("Rastrigin");
	}
	
	@Override
	public double calculate(ArrayList<Double> X) {
		double result = 0;
		for ( Double x : X ) {
			result += (Math.pow(x,2) - 10*Math.cos( 2*Math.PI*(x) ) + 10);
		}
		return result;
	}
	
	@Override
	public ArrayList<Razpon> getDefaultRazponi() {
		return Razpon.getRazponi(-5.12, 5.12, n);
	}

	@Override
	public double getBestPossibleResult() {
		// TODO Auto-generated method stub
		return 0;
	}

}
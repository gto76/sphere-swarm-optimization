package sso.functions.standard;

import java.util.ArrayList;

import sso.functions.Function;
import sso.io.Razpon;

/*
 * Sphere
 */
public class F1 extends Function {

	public F1() {
		super("f1");
	}
	
	@Override
	public double calculate(ArrayList<Double> X) {
		double result = 0;
		for ( Double x : X ) {
			result += Math.pow( x, 2);					
		}
		return result;
	}

	@Override
	public double getBestPossibleResult() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<Razpon> getDefaultRazponi() {
		// TODO Auto-generated method stub
		return null;
	}

}
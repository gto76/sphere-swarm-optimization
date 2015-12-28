package sso.functions.standard;

import java.util.ArrayList;

import sso.functions.Function;
import sso.io.Razpon;


public class F2 extends Function {

	public F2() {
		super("f2");
	}
	
	@Override
	public double calculate(ArrayList<Double> X) {
		
		double result1 = 0;
		for ( Double x : X ) {
			result1 += Math.abs(x);
		}
		
		double result2 = Math.abs( X.get(0) );
		for ( int i = 1; i < X.size(); i++) {
			result2 *= Math.abs( X.get(i) );
		}
		
		return result1 + result2;
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
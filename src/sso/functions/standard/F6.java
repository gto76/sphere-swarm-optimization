package sso.functions.standard;

import java.util.ArrayList;

import sso.functions.Function;
import sso.io.Razpon;


public class F6 extends Function {

	public F6() {
		super("f6");
	}
	
	@Override
	public double calculate(ArrayList<Double> X) {
		double result = 0;
		for ( Double x : X ) {
			result += x + 0.5;
		}
		return Math.pow(result, 2);
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
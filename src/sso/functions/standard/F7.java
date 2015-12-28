package sso.functions.standard;

import java.util.ArrayList;

import sso.functions.Function;
import sso.io.Razpon;


public class F7 extends Function {

	public F7() {
		super("f7");
	}
	
	@Override
	public double calculate(ArrayList<Double> X) {
		double result = 0;
		
		for ( int i = 0; i < X.size(); i++ ) {
			result += (double)(i+1) * Math.pow(X.get(i), 4);
		}
		
		result += Math.random();
		
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
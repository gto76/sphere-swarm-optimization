package sso.functions.standard;

import java.util.ArrayList;

import sso.functions.Function;
import sso.io.Razpon;


public class F5 extends Function {

	public F5() {
		super("f5");
	}
	
	@Override
	public double calculate(ArrayList<Double> X) {
		double result = 0;
		
		for ( int i = 0; i < X.size()-1; i++ ) {
			
			result += 100.0 
					* Math.pow( X.get(i+1) - Math.pow(X.get(i), 2), 2)
					+ Math.pow( X.get(i) - 1, 2);
			
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
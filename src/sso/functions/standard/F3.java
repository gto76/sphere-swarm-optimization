package sso.functions.standard;

import java.util.ArrayList;

import sso.functions.Function;
import sso.io.Razpon;


public class F3 extends Function {

	public F3() {
		super("f3");
	}
	
	@Override
	public double calculate(ArrayList<Double> X) {
		double result = 0;
				
		for ( int i = 0; i < X.size(); i++ ) {
			double res2 = 0;
			for ( int j = 0; j <= i; j++ ) {
				res2 += X.get(j); 
			}					
			result += Math.pow(res2, 2);
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
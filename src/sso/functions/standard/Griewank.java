package sso.functions.standard;

import java.util.ArrayList;

import sso.functions.Function;
import sso.io.Razpon;

/*
 * Griewank's function
 */

public class Griewank extends Function {

	final int n = 5;
	
	public Griewank() {
		super("Griewank");
	}
	
	@Override
	public double calculate(ArrayList<Double> X) {
			
		double prvaOs = X.get(0);
		double res1 = Math.pow( prvaOs, 2);
		double res2 = Math.cos( prvaOs / 1.0 );
		for ( int i = 1; i < X.size(); i++) {
			double itaOs = X.get(i);
			res1 += Math.pow( itaOs, 2);
			res2 *= Math.cos( itaOs / Math.sqrt(i+1.0) );
		}
		
		return (res1 / 4000.0) - res2 + 1.0;
	}
	
	@Override
	public ArrayList<Razpon> getDefaultRazponi() {
		return Razpon.getRazponi(-600, 600, n);
	}

	@Override
	public double getBestPossibleResult() {
		// TODO Auto-generated method stub
		return 0;
	}
}
package sso.functions.rest;

import java.util.ArrayList;

import sso.functions.Function;
import sso.io.Razpon;
import static java.lang.Math.*;

/**
 * Easom's function:
 * 
 */

public class Goldstein extends Function {

		final int n = 2;
		
		public Goldstein() {
			super("Goldstein");
		}
		
		@Override
		public double calculate(ArrayList<Double> X) {
			double result = 0;
			Double[] x = X.toArray(new Double[0]);
			
			result = (1 + pow(x[0]+x[1]+1,2) * (19-14*x[0]+3*pow(x[0],2)-14*x[1]+6*x[0]*x[1]+3*pow(x[1],2) ) )
				* ( 30 + pow(2*x[0]-3*x[1],2) * (18-32*x[0]+12*pow(x[0],2)+48*x[1]-36*x[0]*x[1]+27*pow(x[1],2)) );
			
			
			return result;
		}

		@Override
		public ArrayList<Razpon> getDefaultRazponi() {
			return Razpon.getRazponi(-2, 2, n);
		}

		@Override
		public double getBestPossibleResult() {
			return 3; 
		}

	}

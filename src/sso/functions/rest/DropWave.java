package sso.functions.rest;

import java.util.ArrayList;

import sso.functions.Function;
import sso.io.Razpon;
import static java.lang.Math.*;

/**
 * Drop Wave function:
 * 
 */

public class DropWave extends Function {

		final int n = 2;
		
		public DropWave() {
			super("DropWave");
		}
		
		@Override
		public double calculate(ArrayList<Double> X) {
			double result = 0;
			Double[] x = X.toArray(new Double[0]);
			
			result = -(
					( 1+cos(12*sqrt(pow(x[0],2)+pow(x[1],2))) )
					/
					( 0.5*(pow(x[0],2)+pow(x[1],2))+2 )
				);
			
			return result;
		}

		@Override
		public ArrayList<Razpon> getDefaultRazponi() {
			return Razpon.getRazponi(-5.12, 5.12, 2);
		}

		@Override
		public double getBestPossibleResult() {
			return -1; //TODO ? 
		}

	}

package sso.functions.rest;

import java.util.ArrayList;

import sso.functions.Function;
import sso.io.Razpon;
import static java.lang.Math.*;

/**
 * SixHumpCamelBack's function:
 * 
 */

public class SixHumpCamelBack extends Function {

		final int n = 2;
		
		public SixHumpCamelBack() {
			super("SixHumpCamelBack");
		}
		
		@Override
		public double calculate(ArrayList<Double> X) {
			double result = 0;
			Double[] x = X.toArray(new Double[0]);
			
			result = (4-2.1*pow(x[0],2)+(pow(x[0],4)/3)) * pow(x[0],2)
				+ x[0]*x[1]
				+ (-4+4*pow(x[1],2))*pow(x[1],2);
			
			return result;
		}

		@Override
		public ArrayList<Razpon> getDefaultRazponi() {
			ArrayList<Razpon> out = new ArrayList<Razpon>(2);
			out.add(new Razpon(-3, 3));
			out.add(new Razpon(-2, 2));
			return out;
		}

		@Override
		public double getBestPossibleResult() {
			return -1.0315; 
		}

	}

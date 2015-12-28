package sso.functions.rest;

import java.util.ArrayList;

import sso.functions.Function;
import sso.io.Razpon;
import static java.lang.Math.*;

/**
 * Branins's function:
 * 
 */

public class Branins extends Function {

		final int n = 2;
		final double a = 1;
		final double b = 5.1/(4*pow(PI,2));
		final double c = 5/PI;
		final double d = 6;
		final double e = 10; 
		final double f = 1/(8*PI); 
		
		public Branins() {
			super("Branins");
		}
		
		@Override
		public double calculate(ArrayList<Double> X) {
			double result = 0;
			Double[] x = X.toArray(new Double[0]);
			
			result = a * pow( x[1]-b*pow(x[0],2)+c*x[0]-d ,2) + e*(1-f)*cos(x[0]) + e;
			
			return result;
		}

		@Override
		public ArrayList<Razpon> getDefaultRazponi() {
			return Razpon.getRazponi(-15, 15, n); //TODO ?
		}

		@Override
		public double getBestPossibleResult() {
			return 0.397887; 
		}

	}

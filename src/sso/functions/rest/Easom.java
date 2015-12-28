package sso.functions.rest;

import java.util.ArrayList;

import sso.functions.Function;
import sso.io.Razpon;
import static java.lang.Math.*;

/**
 * Easom's function:
 * Needle in hay.
 */

public class Easom extends Function {

		final int n = 2;
		
		public Easom() {
			super("Easom");
		}
		
		@Override
		public double calculate(ArrayList<Double> X) {
			double result = 0;
			Double[] x = X.toArray(new Double[0]);
			
			result = -cos(x[0]) * cos(x[1]) * exp(-pow(x[0]-PI,2)-pow(x[1]-PI,2));
			
			
			return result;
		}

		@Override
		public ArrayList<Razpon> getDefaultRazponi() {
			return Razpon.getRazponi(-100, 100, n); //TODO ?
		}

		@Override
		public double getBestPossibleResult() {
			return -1; 
		}

	}

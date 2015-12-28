package sso.functions.rest;

import java.util.ArrayList;

import sso.functions.Function;
import sso.io.Razpon;
import static java.lang.Math.*;

/**
 * Rosenbrock's function:
 * 
 */


public class RosenbrockShifted extends Function {

		final int n = 100;
		
		public RosenbrockShifted() {
			super("RosenbrockShifted");
		}
		
		@Override
		public double calculate(ArrayList<Double> X) {
			double result = 0;
			for (int i = 0; i < (X.size()-1); i++) {
				result += 100*pow(X.get(i+1) - pow(X.get(i),2), 2) + pow(1 - X.get(i),2);
			}
			return result + 390;
		}

		@Override
		public ArrayList<Razpon> getDefaultRazponi() {
			return Razpon.getRazponi(-100, 100, n);
		}

		@Override
		public double getBestPossibleResult() {
			return 390;
		}

	}

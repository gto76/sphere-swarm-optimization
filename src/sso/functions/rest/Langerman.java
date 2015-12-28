package sso.functions.rest;

import java.util.ArrayList;

import sso.functions.Function;
import sso.io.Razpon;
import static java.lang.Math.*;

/**
 * Langerman 's function:
 * 
 */

public class Langerman extends Function {

		final int n = 5;
		final int m = 5; 
		
		final double a[][] = {
				{9.681, 0.667, 4.783, 9.095, 3.517, 9.325, 6.544, 0.211, 5.122, 2.020},
				{9.400, 2.041, 3.788, 7.931, 2.882, 2.672, 3.568, 1.284, 7.033, 7.374},
				{8.025, 9.152, 5.114, 7.621, 4.564, 4.711, 2.996, 6.126, 0.734, 4.982},
				{2.196, 0.415, 5.649, 6.979, 9.510, 9.166, 6.304, 6.054, 9.377, 1.426},
				{8.074, 8.777, 3.467, 1.867, 6.708, 6.349, 4.534, 0.276, 7.633, 1.567}};

		final double c[] = {0.806,0.517,0.1,0.908,0.965};
		
		public Langerman () {
			super("Langerman ");
		}
		
		@Override
		public double calculate(ArrayList<Double> X) {
			double result = 0;
			Double[] x = X.toArray(new Double[0]);
			
			for (int i = 0; i < m; i++) {
				int tmp1 = 0;
				for (int j = 0; j < n; j++) {
					tmp1 += pow(x[j] - a[i][j], 2);
				}
				result += c[i] * exp(-(1/PI)*tmp1) * cos(PI*tmp1);
			}
			
			return result;
		}

		@Override
		public ArrayList<Razpon> getDefaultRazponi() {
			return Razpon.getRazponi(-2.048, 2.048, n); //TODO
		}

		@Override
		public double getBestPossibleResult() {
			return -0.0000000001; //TODO
		}

	}

package sso.functions.rest;

import java.util.ArrayList;

import sso.functions.Function;
import sso.io.Razpon;
import static java.lang.Math.*;

/**
 * Schubert function:
 * 
 */

public class Schubert extends Function {

		final int n = 2;
		
		public Schubert() {
			super("Schubert");
		}
		
		@Override
		public double calculate(ArrayList<Double> X) {
			double result = 0;
			Double[] x = X.toArray(new Double[0]);
			double temp1 = 0, temp2 = 0;
		
			/* version 1: 
			for (int i = 1; i <= 5; i++) {
				temp1 += i*cos((i+1)*x[0]+1);
				temp2 += i*cos((i+1)*x[1]+1);
			}
			
			result = -temp1*temp2;
			 */
			
			for (int i = 1; i <= 5; i++) {
				temp1 += i*cos((i+1)*x[0]+i);
				temp2 += i*cos((i+1)*x[1]+i);
			}
			
			result = temp1*temp2;
			
			return result;
		}

		@Override
		public ArrayList<Razpon> getDefaultRazponi() {
			return Razpon.getRazponi(-5.12, 5.12, 2);
		}

		@Override
		public double getBestPossibleResult() {
			return -186.7308; 
		}

	}

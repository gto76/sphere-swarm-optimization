package sso.functions.rest;

import java.util.ArrayList;

import sso.functions.Function;
import sso.io.Razpon;
import static java.lang.Math.*;

/**
 * Fift function of DeJung function:
 * 
 */

public class FiftDeJung extends Function {

		final int n = 2;
		
		public FiftDeJung() {
			super("FiftDeJung");
		}
		
		@Override
		public double calculate(ArrayList<Double> X) {
			double result = 0;
			double temp = 0;
			Double[] x = X.toArray(new Double[0]);
			
			for (int i = -2; i < 2; i++) {
				for (int j = -2; j < 2; j++) {
					temp = 5*(i+2)+j+3+pow(x[0]-16*j,6)+pow(x[1]-16*i,6);
					result += pow(temp,-1);
				}
			}
			
			result = pow(0.002+result,-1);
				
			return result;
		}

		@Override
		public ArrayList<Razpon> getDefaultRazponi() {
			return Razpon.getRazponi(-65.536, 65.536, 2);
		}

		@Override
		public double getBestPossibleResult() {
			return 0; //TODO ? 
		}

	}

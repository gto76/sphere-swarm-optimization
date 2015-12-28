package sso.functions.rest;

import java.util.ArrayList;

import sso.functions.Function;
import sso.io.Razpon;
import static java.lang.Math.*;

/**
 * Michelawitz  's function:
 * 
 */

public class Michelawitz extends Function {

		final int n = 5;
		final int m = 5; 
		
		public Michelawitz  () {
			super("Michelawitz");
		}
		
		@Override
		public double calculate(ArrayList<Double> X) {
			double result = 0;
			Double[] x = X.toArray(new Double[0]);
			
			for (int i = 0; i < x.length; i++) {
				result -= sin(x[i]) * pow(sin((i+1)*pow(x[i],2)/PI) ,2*m);
			}
			
			return result;
		}

		@Override
		public ArrayList<Razpon> getDefaultRazponi() {
			return Razpon.getRazponi(0, PI, n); //TODO
		}

		@Override
		public double getBestPossibleResult() {
			return -4.687; //za n = 5, za n = 10 -> -9.66
		}

	}

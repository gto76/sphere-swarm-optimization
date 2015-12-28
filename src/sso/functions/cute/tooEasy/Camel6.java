package sso.functions.cute.tooEasy;

import java.util.ArrayList;

import sso.functions.Function;
import sso.io.Razpon;
import static java.lang.Math.*;

/**
 * 
#   Source: Six hump camel in
#   L. C. W. Dixon and G. P. Szego (Eds.)
#   Towards Global Optimization
#   North Holland, 1975.

#   SIF input: A.R. Conn May 1995

#   classification OBR2-AN-2-0
 *
 *n=2
 *tezavnost=1
 */

public class Camel6 extends Function {

	public Camel6() {
		super("Camel6");
	}
	
	@Override
	public double calculate(ArrayList<Double> X) {
		double res = 0;
		Double[] x = X.toArray(new Double[0]);
		res = 4*pow(x[0],2) - 2.1*pow(x[0],4) 
			+ pow(x[0],6)/3 + x[0]*x[1] - 4*pow(x[1],2) + 4*pow(x[1],4);
		return res;
	}

	@Override
	public ArrayList<Razpon> getDefaultRazponi() {
		ArrayList<Razpon> out = new ArrayList<Razpon>();
		out.add(new Razpon(-3, 3));
		out.add(new Razpon(-1.5, 1.5));
		return out;
	}

	@Override
	public double getBestPossibleResult() {
		return -1.0316000;
	}

}

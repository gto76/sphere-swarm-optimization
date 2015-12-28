package sso.functions.cute.tooEasy;

import java.util.ArrayList;

import sso.functions.Function;
import sso.io.Razpon;
import static java.lang.Math.*;

/**
 * n=2
 * tezavnost=1
 */
public class Hs005 extends Function {

	public Hs005() {
		super("Hs005");
	}
	
	@Override
	public double calculate(ArrayList<Double> X) {
		Double[] x = X.toArray(new Double[0]);
		double res = 0;
		res = sin(x[1-1]+x[2-1]) + pow(x[1-1]-x[2-1],2) - 1.5*x[1-1] + 2.5*x[2-1] + 1;
		return res;
	}

	@Override
	public ArrayList<Razpon> getDefaultRazponi() {
		ArrayList<Razpon> out = new ArrayList<Razpon>();
		out.add(new Razpon(-1.5, 4));
		out.add(new Razpon(-3, 3));
		return out;
	}

	@Override
	public double getBestPossibleResult() {
		// TODO Auto-generated method stub
		return -1.9132000;
	}

}

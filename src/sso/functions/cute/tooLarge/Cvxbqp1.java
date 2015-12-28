package sso.functions.cute.tooLarge;

import java.util.ArrayList;

import sso.functions.Function;
import sso.io.Razpon;
import static java.lang.Math.*;

/**
 *  classification QBR2-AN-V-0
 *
 */

public class Cvxbqp1 extends Function {

	public Cvxbqp1() {
		super("Cvxbqp1");
	}
	
	@Override
	public double calculate(ArrayList<Double> X) {
		Double[] x = X.toArray(new Double[0]);
		double res = 0;
		final int N = X.size();
		for (int i = 1; i <= N; i++) {
			res += 0.5 * i * pow( x[i-1] + x[(2*i-1)%N] + x[(3*i-1)%N], 2);
		}
		return res;
	}

	@Override
	public ArrayList<Razpon> getDefaultRazponi() {
		return Razpon.getRazponi(0.1, 10.0, 10000);
	}

	@Override
	public double getBestPossibleResult() {
		// TODO Auto-generated method stub
		return 0;
	}

}

package sso.functions.cute;

import java.util.ArrayList;

import sso.functions.Function;
import sso.io.Razpon;
import static java.lang.Math.*;

/**
 * classification OBR2-AN-V-V
 * n=120
 * tezavnost=3
 * 
 * povp = 130000
 */
public class Explin extends Function {

	public Explin() {
		super("Explin");
	}

	@Override
	public double calculate(ArrayList<Double> X) {
		Double[] x = X.toArray(new Double[0]);
		double res = 0;
		final int m = 10;
		final int n = X.size();

		for (int i = 1; i <= m; i++) {
			res += exp(0.1*x[i-1]*x[i]);
		}
		for (int i = 1; i <= n; i++) {
			res += -10.0*i*x[i-1];
		}	
		return res;
	}

	@Override
	public ArrayList<Razpon> getDefaultRazponi() {
		return Razpon.getRazponi(0, 10, 120);
	}

	@Override
	public double getBestPossibleResult() {
		return -723756.3000000;
	}

}

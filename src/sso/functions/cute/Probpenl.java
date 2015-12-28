package sso.functions.cute;

import java.util.ArrayList;

import sso.functions.Function;
import sso.io.Razpon;
import static java.lang.Math.*;

/**
 * n=500
 * tezavnost = 3
 */
public class Probpenl extends Function {

	final int n = 500;
	
	public Probpenl() {
		super("Probpenl");
	}
	
	@Override
	public double calculate(ArrayList<Double> X) {
		Double[] x = X.toArray(new Double[0]);
		double res = 0;
		double temp1 = 0;
		for (int i = 1; i <= n; i++) {
			temp1 += x[i-1] - 1;
		}
		for (int i = 1; i <= n-1; i++) {
			res += (x[i-1]+x[i])*0.0001*exp(-x[i-1]*x[i])/n + 100*pow(temp1,2);
		}
		return res;		
	}
	
	@Override
	public ArrayList<Razpon> getDefaultRazponi() {
		return Razpon.getRazponi(-5, 5, n);
	}

	@Override
	public double getBestPossibleResult() {
		// TODO Auto-generated method stub
		return -374026.4045000;
	}
	
}

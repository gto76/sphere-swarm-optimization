package sso.functions.cute.tooLarge;

import java.util.ArrayList;

import sso.functions.Function;
import sso.io.Razpon;
import static java.lang.Math.*;

/**
 * n=100
 * tezavnost = 3
 */
public class S368 extends Function {

	final int n = 100;
	//final int m = 6;
	
	public S368() {
		super("S368");
	}
	
	@Override
	public double calculate(ArrayList<Double> X) {
		Double[] x = X.toArray(new Double[0]);
		double temp1 = 0, temp2 = 0;
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				temp1 += (-pow(x[i-1],2)*pow(x[j-1],4));
			}
		}
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				temp2 += (pow(x[i-1],3)*pow(x[j-1],3));
			}
		}
		return temp1 + temp2;		
	}
	
	@Override
	public ArrayList<Razpon> getDefaultRazponi() {
		return Razpon.getRazponi(0, 1, n);
	}

	@Override
	public double getBestPossibleResult() {
		return -156.1875000;
	}
	
}

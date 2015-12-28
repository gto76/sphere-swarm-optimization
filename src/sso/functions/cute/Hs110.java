package sso.functions.cute;

import java.util.ArrayList;

import sso.functions.Function;
import sso.io.Razpon;
import static java.lang.Math.*;

/**
 * n=10
 * tezavnost=2
 * 
all x = 9.35027
obj =  -45.7785

povp = 0.5

DELA!!!
 */
public class Hs110 extends Function {

	public Hs110() {
		super("Hs110");
	}
	
	@Override
	public double calculate(ArrayList<Double> X) {
		Double[] x = X.toArray(new Double[0]);
		double res = 0;
		double temp1 = 0, temp2 = 1;
		for (int i = 1; i <= 10; i++) {
			temp1 += pow(log(x[i-1]-2),2) + pow(log(10-x[i-1]),2);
		}
		for (int i = 1; i <= 10; i++) {
			temp2 *= x[i-1];
		}
		temp2 = pow(temp2, 0.2);
		res = temp1 - temp2;
		return res;		
	}
	
	@Override
	public ArrayList<Razpon> getDefaultRazponi() {
		return Razpon.getRazponi(2.001, 9.999, 10);
	}

	@Override
	public double getBestPossibleResult() {
		return -45.7785000;
	}

}

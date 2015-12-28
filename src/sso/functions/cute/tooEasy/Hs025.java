package sso.functions.cute.tooEasy;

import java.util.ArrayList;

import sso.functions.Function;
import sso.io.Razpon;
import static java.lang.Math.*;

/**
 * n=3
 * tezavnost = 1
 */
public class Hs025 extends Function {

	final double[] u;
	
	public Hs025() {
		super("Hs025");
		u = new double[99];
		for (int i = 1; i <= 99; i++) {
			u[i-1] = 25 + pow(-50*log((double)i/100),2.0/3.0);
		}
	}

	@Override
	public double calculate(ArrayList<Double> X) {
		Double[] x = X.toArray(new Double[0]);
		double res = 0;
		
		for (int i = 1; i <= 99; i++) {
			res += pow( -i/100.0 + exp( -pow(u[i-1]-x[2-1],x[3-1]) /x[1-1] ), 2);	
		}
		return res;		
	}
	
	@Override
	public ArrayList<Razpon> getDefaultRazponi() {
		ArrayList<Razpon> out = new ArrayList<Razpon>();
		out.add(new Razpon(1.0/10.0, 100));
		out.add(new Razpon(0, 25.6));
		out.add(new Razpon(0, 5));
		return out;
	}

	@Override
	public double getBestPossibleResult() {
		// TODO Auto-generated method stub
		return 0.0;
	}

}

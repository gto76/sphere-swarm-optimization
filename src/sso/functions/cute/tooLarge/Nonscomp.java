package sso.functions.cute.tooLarge;

import java.util.ArrayList;

import sso.functions.Function;
import sso.io.Razpon;
import static java.lang.Math.*;

public class Nonscomp extends Function {

	final int n = 10000;
	
	public Nonscomp() {
		super("Nonscomp");
	}
	
	@Override
	public double calculate(ArrayList<Double> X) {
		Double[] x = X.toArray(new Double[0]);
		double res = 0;
		double temp1 = 0, temp2 = 0;
		temp1 = pow(x[1] - 1,2);
		for (int i = 2; i <= n; i++) {
			temp2 += 4*pow(x[i-1]-x[i-2],2);
		}
		res = temp1 + temp2;
		return res;		
	}
	
	@Override
	public ArrayList<Razpon> getDefaultRazponi() {
		ArrayList<Razpon> out = new ArrayList<Razpon>();
		for (int i = 1; i <= n; i++) {
			if ( (i%3) == 0 ) {
				out.add(new Razpon(1, 100));
			}
			else {
				out.add(new Razpon(-100, 100));
			}
		}
		return out;
	}

	@Override
	public double getBestPossibleResult() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}

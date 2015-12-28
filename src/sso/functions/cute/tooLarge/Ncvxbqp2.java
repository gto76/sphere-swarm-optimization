package sso.functions.cute.tooLarge;

import java.util.ArrayList;

import sso.functions.Function;
import sso.io.Razpon;
import static java.lang.Math.*;

public class Ncvxbqp2 extends Function {

	final int n = 10000;
	final int m = n/2;
	final int Nplus = n/2;
	
	public Ncvxbqp2() {
		super("Ncvxbqp2");
	}
	
	@Override
	public double calculate(ArrayList<Double> X) {
		Double[] x = X.toArray(new Double[0]);
		double res = 0;
		double temp1 = 0, temp2 = 0;
		for (int i = 1; i <= Nplus; i++) {
			temp1 += 0.5*i*pow(x[i-1]+x[ ((2*i-1) % n) ] + x[ ((3*i-1) % n) ],2);
		}
		for (int i = Nplus+1; i <= n; i++) {
			temp2 += 0.5*i*pow(x[i-1]+x[ ((2*i-1) % n) ] + x[ ((3*i-1) % n) ],2);
		}
		res = temp1 - temp2;
		return res;		
	}
					
	@Override
	public ArrayList<Razpon> getDefaultRazponi() {
		return Razpon.getRazponi(0.1, 10, n);
	}

	@Override
	public double getBestPossibleResult() {
		// TODO Auto-generated method stub
		return 0;
	}

}

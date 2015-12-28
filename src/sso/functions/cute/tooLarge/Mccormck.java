package sso.functions.cute.tooLarge;

import java.util.ArrayList;

import sso.functions.Function;
import sso.io.Razpon;
import static java.lang.Math.*;

public class Mccormck extends Function {

	public Mccormck() {
		super("Mccormck");
	}
	
	@Override
	public double calculate(ArrayList<Double> X) {
		Double[] x = X.toArray(new Double[0]);
		double res = 0;
		for (int i = 1; i <= x.length-1; i++) {
			res = (-1.5*x[i-1]+2.5*x[i+1-1]+1.0+pow(x[i-1]-x[i+1-1],2)+sin(x[i-1]+x[i+1-1]));
		}
		return res;		
	}
			
	@Override
	public ArrayList<Razpon> getDefaultRazponi() {
		return Razpon.getRazponi(-1.5, 3.0, 50000);
	}

	@Override
	public double getBestPossibleResult() {
		// TODO Auto-generated method stub
		return 0;
	}

}

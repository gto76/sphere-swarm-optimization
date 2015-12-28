package sso.functions.cute;

import java.util.ArrayList;

import sso.functions.Function;
import sso.io.Razpon;

/**
 * n=12
 * tezavnost=2
 * 
#   classification QBR2-AN-V-V
 * 
x(1) = 6.37862
x(2) =  10
x(3) =  10
x(4) =  10
x(5) =  10
x(6) =  10
x(7) =  10
x(8) =  10
x(9) =  10
x(10) =  10
x(11) =  10
x(12) =  10
obj =  -7200

povp = 150
 */
public class Qudlin extends Function {

	final int n = 12;
	final int m = 6;
	
	public Qudlin() {
		super("Qudlin");
	}
	
	@Override
	public double calculate(ArrayList<Double> X) {
		Double[] x = X.toArray(new Double[0]);
		double temp1 = 0, temp2 = 0;
		for (int i = 1; i <= n; i++) {
			temp1 += -i*10*x[i-1];
		}
		for (int i = 1; i <= m; i++) {
			temp2 += x[i-1]*x[i];
		}
		return temp1 + temp2;		
	}
	
	@Override
	public ArrayList<Razpon> getDefaultRazponi() {
		return Razpon.getRazponi(0, 10, n);
	}

	@Override
	public double getBestPossibleResult() {
		// TODO Auto-generated method stub
		return -7200.0;
	}
	
}

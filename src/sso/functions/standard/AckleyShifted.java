package sso.functions.standard;

import java.util.ArrayList;

import sso.functions.Function;
import sso.io.Razpon;

/*
 * Ackley's function
 */

public class AckleyShifted extends Function {

	final int n = 100;
	
	public AckleyShifted() {
		super("AckleyShifted");
	}
		
	@Override
	public double calculate(ArrayList<Double> X) {
		
		double res1 = 0;
		double res2 = 0;
		for ( Double x : X ) {
			res1 += Math.pow(x, 2);
			res2 += Math.cos(2.0*Math.PI*(x));
		}
		
		double result = -20.0 * Math.exp(-0.2*Math.sqrt(res1/(double)X.size()))
					-Math.exp(res2/(double)X.size()) - 120.0 + Math.E;
		return result;
	}
	
	@Override
	public ArrayList<Razpon> getDefaultRazponi() {
		return Razpon.getRazponi(-32, 32, n);
	}

	@Override
	public double getBestPossibleResult() {
		return -140;
	}

}



/*
a =   20;
b =  0.2;
c = 2*pi;
d =  5.7;
f =  0.8;
n =    2;

z = (1/f)*( -a*exp(-b*sqrt((1/n)*(x.^2+y.^2))) - ...
    exp((1/n)*(cos(c*x) + cos(c*y))) + ...
    a + exp(1) + d);
*/
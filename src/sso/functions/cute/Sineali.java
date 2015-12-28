package sso.functions.cute;

import java.util.ArrayList;

import sso.functions.Function;
import sso.io.Razpon;
import static java.lang.Math.*;

/**
 * 
#   Source: on original idea by
#   Ali Bouriacha, private communication.

#   SIF input: Nick Gould and Ph. Toint, October, 1993.

#   classification OBR2-AN-V-0

 * n=20
 * tezavnost = 3
 * 
 * 
X(1) = 	-0.5207137
X(2) = 	-1.300132
X(3) = 	0.1197267
X(4) = 	-1.555699
X(5) = 	0.8491583
X(6) = 	-0.8498714
X(7) = 	-0.8484271
X(8) = 	-0.8510223
X(9) = 	-0.8465226
X(10) = 	-0.8542197
X(11) = 	-0.8410879
X(12) = 	-0.8633767
X(13) = 	-0.8253715
X(14) = 	-0.8895582
X(15) = 	-0.7794883
X(16) = 	-0.9631884
X(17) = 	-0.6430701
X(18) = 	-1.157259
X(19) = 	-0.2315647
X(20) = 	-1.517240
obj = -1900.999

povp = 150

DELA!!!
 */

public class Sineali extends Function {

	final int n = 20;
	
	public Sineali() {
		super("Sineali");
	}
	
	@Override
	public double calculate(ArrayList<Double> X) {
		Double[] x = X.toArray(new Double[0]);
		double temp1 = 0, temp2 = 0;
		temp1 =	sin(x[0]-1);
		for (int i = 2; i <= n; i++) {
			temp2 += 100*sin(x[i-1]-pow(x[i-2],2));
		}
		return temp1 + temp2;		
	}
	
	@Override
	public ArrayList<Razpon> getDefaultRazponi() {
		ArrayList<Razpon> out = new ArrayList<Razpon>();
		out.add(new Razpon(-1.5*PI, 0.5*PI));
		out.addAll(Razpon.getRazponi(sqrt(PI)-2*PI, sqrt(PI), n-1));
		return out;
	}

	@Override
	public double getBestPossibleResult() {
		return -1900.9990000;
	}
	
}

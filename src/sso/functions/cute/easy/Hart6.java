package sso.functions.cute.easy;

import java.util.ArrayList;

import sso.functions.Function;
import sso.io.Razpon;
import static java.lang.Math.*;

/**
 * 
#   Source: Hartman problem 6 in
#   L. C. W. Dixon and G. P. Szego (Eds.)
#   Towards Global Optimization
#   North Holland, 1975.
#   Paper 9, page 163.

#   SIF input: A.R. Conn May 1995

#   classification OBR2-AN-6-0

 * n = 6
 * tezavnost=2
 * 
x(1) =     0.2016625606
x(2) =     0.1476768507
x(3) =     0.4768667692
x(4) =     0.2753176613
x(5) =     0.3116642775
x(6) =     0.6572686392
obj =    -3.3228868916

DELA!!!
 */
public class Hart6 extends Function {
	
	double[] c = {1.0, 1.2, 3.0, 3.2};
	double[][] a = 
			{{10.0, 0.05, 17.0, 3.5, 1.7, 8.0}, 
			{0.05, 10.0, 17.0, 0.1, 8.0, 14.0}, 
			{3.0, 3.5, 1.7, 10.0, 17.0, 8.0}, 
			{17.0, 8.0, 0.05, 10.0, 0.1, 14.0}};
	double[][] p = 
			{{0.1312, 0.1696, 0.5569, 0.0124, 0.8283, 0.5886},
			{0.2329, 0.4135, 0.8307, 0.3736, 0.1004, 0.9991},
			{0.2348, 0.1451, 0.3522, 0.2883, 0.3047, 0.665},
			{0.4047, 0.8828, 0.8732, 0.5743, 0.1091, 0.0381}};

	public Hart6() {
		super("Hart6");
	}
	
	@Override
	public double calculate(ArrayList<Double> X) {
		Double[] x = X.toArray(new Double[0]);
		double res = 0;
		for (int i = 1; i <= 4; i++) {
			double temp = 0;
			for (int j = 1; j <= 6; j++) {
				temp += a[i-1][j-1]* pow(x[j-1]-p[i-1][j-1],2);
			}
			temp *= -1;
			res += c[i-1]*exp(temp);
		}
		res *= -1;
		return res;
	}

	@Override
	public ArrayList<Razpon> getDefaultRazponi() {
		return Razpon.getRazponi(0, 1, 6);
	}

	@Override
	public double getBestPossibleResult() {
		return -3.3229000;
	}

}

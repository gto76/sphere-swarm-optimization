package sso.functions.cute.tooEasy;

import java.util.ArrayList;

import sso.functions.Function;
import sso.io.Razpon;
import static java.lang.Math.*;

/**
 * n=8
 * tezavnost = 1.5
 */
public class Maxlika extends Function {
	
	final int n = 235;
	final double[] y = {95, 105, 110, 110, 110, 110, 115, 115, 115, 115, 120, 120, 120, 120, 120, 120, 120, 120, 120, 120, 120, 120, 120, 120, 120, 125, 125, 125             ,125             ,125             ,125             ,125             ,125             ,125             ,125             ,125             ,125             ,125             ,125             ,125             ,130             ,130             ,130             ,130             ,130             ,130             ,130             ,130             ,130             ,130             ,130             ,130             ,130             ,130             ,130             ,135         ,135         ,135         ,135         ,135, 135,135,135,135,135,135,135,135,140,140,140,140,140,140,140,140,140,140,140,140,140,140,140,140,140,140,140,140,140,145,145,145,145,145,145,145,145,145,145,145,145,150,150,150,150,150,150,150,150,150,150,150,150,150,150,150,150,150,155,155, 155 , 155 , 160 , 160 , 160 , 160 , 160 , 160 , 160 , 160 , 160 , 160 , 160 , 160 , 160 , 160 , 160 , 160 , 160 , 160 , 160 , 160 , 165 , 165 , 165 , 165 , 165 , 165 , 165 , 165 , 170 , 170 , 170 , 170 , 170 , 170 , 170 , 170 , 170 , 170 , 170 , 170 , 170 , 170 , 170 , 170 , 170 , 175 , 175 , 175 , 175 , 175 , 175 , 175 , 175 , 180, 180, 180, 180, 180, 180,  185,  185,  185,  185,  185,  185,  190,  190,  190,  190,  190,  190,  190,  195,  195,  195,  195,  200,  200,  200,  205,  205,  205,  210,  210,  210,  210,  210,  210,  210,  210,  215,  220,  220,  220,  220,  220,  220,  230,  230,  230,  230,  230,  235,  240,  240,  240,  240,  240,  240,  240,  245,  250,  250};

	public Maxlika() {
		super("Maxlika");
	}
		
	@Override
	public double calculate(ArrayList<Double> X) {
		Double[] x = X.toArray(new Double[0]);
		double res = 0;

		double a[] = new double[n];
		double b[] = new double[n];
		double c[] = new double[n];
		
		for (int i = 1; i <= n; i++) {
			a[i-1] = x[1-1] / x[6-1] * exp(-pow(y[i-1] - x[3-1],2) / (2 * pow(x[6-1],2)));
			b[i-1] = x[2-1] / x[7-1] * exp(-pow(y[i-1] - x[4-1],2) / (2 * pow(x[7-1],2)));
			c[i-1] = (1 - x[2-1] - x[1-1]) / x[8-1] * exp(-pow(y[i-1] - x[5-1],2) / (2 * pow(x[8-1],2)));
		}
		for (int i = 1; i <= n; i++) {
			res += log((a[i-1] + b[i-1] + c[i-1]) / sqrt(2 * PI));
		}	
		res *= -1;
		return res;
	}
	
	@Override
	public ArrayList<Razpon> getDefaultRazponi() {
		ArrayList<Razpon> out = new ArrayList<Razpon>();
		out.add(new Razpon(.001, .499));
		out.add(new Razpon(.001, .449));
		out.add(new Razpon(100, 180));
		out.add(new Razpon(130, 210));
		out.add(new Razpon(170, 240));
		out.add(new Razpon(5, 25));
		out.add(new Razpon(5, 25));
		out.add(new Razpon(5, 25));
		return out;
	}

	@Override
	public double getBestPossibleResult() {
		// TODO Auto-generated method stub
		return 1136.3600000;
	}

}

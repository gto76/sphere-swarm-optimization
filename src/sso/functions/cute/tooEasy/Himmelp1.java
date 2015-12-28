package sso.functions.cute.tooEasy;

import java.util.ArrayList;

import sso.functions.Function;
import sso.io.Razpon;
import static java.lang.Math.*;

/**
x(1) =    81.1916340065
x(2) =    69.1579052108
obj =   -62.0538693754

 * n=2
 * tezavnost=1
 */
public class Himmelp1 extends Function {
/*
	double[] b = {75.1963666677, -3.8112755343, 0.1269366345, -0.0020567665,
			0.103450d-4, -6.8306567613, .0302344793, -0.0012813448, 0.352599d-4,
			-0.2266d-6, 0.2564581253, -.003460403, 0.135139d-4, -28.1064434908,
			-0.52375d-5, -0.63d-8, 0.7d-9, 0.0003405462, -0.16638d-5, -2.8673112392};
*/

	double[] b = {75.1963666677, -3.8112755343, 0.1269366345, -0.0020567665,
			0.0000103450, -6.8306567613, 0.0302344793, -0.0012813448, 0.0000352599,
			-0.0000002266, 0.2564581253, -0.003460403, 0.0000135139, -28.1064434908,
			-0.0000052375, -0.0000000063, 0.0000000007, 0.0003405462, -0.0000016638, -2.8673112392};

	
	public Himmelp1() {
		super("Himmelp1");
	}
	
	@Override
	public double calculate(ArrayList<Double> X) {
		Double[] x = X.toArray(new Double[0]);
		double res = 0;
		
		res = -b[2-1]*x[1-1]-b[6-1]*x[2-1]-b[1-1]
		-( b[3-1]*pow(x[1-1],2) + b[4-1]*pow(x[1-1],3) + b[5-1]*pow(x[1-1],4) + 
		x[2-1]*(b[7-1]*x[1-1] + b[8-1]*pow(x[1-1],2) + b[9-1]*pow(x[1-1],3) + b[10-1]*pow(x[1-1],4)) +
		b[11-1]*pow(x[2-1],2)+b[12-1]*pow(x[2-1],3)+b[13-1]*pow(x[2-1],4) + b[14-1]/(1+x[2-1]) + 
		(b[18-1]*x[1-1] + b[15-1]*pow(x[1-1],2) + b[16-1]*pow(x[1-1],3))*pow(x[2-1],2) 
		+ (b[17-1]*pow(x[1-1],3)+b[19-1]*x[1-1])*pow(x[2-1],3) +
		b[20-1]*exp(0.0005*x[1-1]*x[2-1]) );
		
		return res;
	}

	@Override
	public ArrayList<Razpon> getDefaultRazponi() {
		ArrayList<Razpon> out = new ArrayList<Razpon>();
		out.add(new Razpon(0, 95));
		out.add(new Razpon(0, 75));
		return out;
	}

	@Override
	public double getBestPossibleResult() {
		// TODO Auto-generated method stub
		return -62.0539000;
	}

}

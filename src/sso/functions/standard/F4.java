package sso.functions.standard;

import java.util.ArrayList;

import sso.functions.Function;
import sso.io.Razpon;


public class F4 extends Function {

	public F4() {
		super("f4");
	}
	
	// se pocekirat ce slucajno noso misljeni max indeksi
	@Override
	public double calculate(ArrayList<Double> X) {
		double result = Math.abs(X.get(0));
		for ( Double x : X ) {
			if ( Math.abs(x) > result ) {
				result = Math.abs(x);
			}
		}
		return result;
	}

	@Override
	public double getBestPossibleResult() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<Razpon> getDefaultRazponi() {
		// TODO Auto-generated method stub
		return null;
	}

}
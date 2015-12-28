package sso.functions.standard;

import java.util.ArrayList;

import sso.functions.Function;
import sso.io.Razpon;

/*
 * Max Ones: stevilo enk v vektorju.
 */

public class FB1 extends Function {

	public final int n = 50;
	
	public FB1() {
		super("Max Ones");
	}

	@Override
	public double calculate(ArrayList<Double> xxx) {
		int count = 0;
		for ( Double x : xxx ) {
			if ( x >= 0 ) {
				count++;
			}
		}
		//return 1.0 / (count+1);
		return xxx.size() - count;
	}

	@Override
	public ArrayList<Razpon> getDefaultRazponi() {
		return Razpon.getRazponi(-1, 1, n);
	}

	@Override
	public double getBestPossibleResult() {
		return 0;
	}
}

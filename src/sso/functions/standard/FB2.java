package sso.functions.standard;

import java.util.ArrayList;

import sso.functions.Function;
import sso.io.Razpon;

/*
 * Leading Ones: stevilo enk do prve nicle
 */

public class FB2 extends Function {

	public final int n = 50;
	
	public FB2() {
		super("Leading Ones");
	}

	@Override
	public double calculate(ArrayList<Double> xxx) {
		int i = 0;
		for (; i < xxx.size(); i++) {
			if ( xxx.get(i) < 0 ) {
				break;
			}
		}
		//return 1.0 / (i+1);
		return xxx.size() - i;
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

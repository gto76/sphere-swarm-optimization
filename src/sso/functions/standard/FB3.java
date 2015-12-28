package sso.functions.standard;

import java.util.ArrayList;

import sso.functions.Function;
import sso.io.Razpon;

/*
 * Leading Ones Blocks: kolk blokov po m enk obstaja
 */

public class FB3 extends Function {

	public final int n = 50;
	public final int m = 3;
	
	public FB3() {
		super("Leading Ones Blocks");
	}

	@Override
	public double calculate(ArrayList<Double> xxx) {
		int count = 0;
		int out = 0;
		for (int i = 0; i < xxx.size(); i++) {
			if ( xxx.get(i) >= 0 ) {
				count++;
			}
			else {
				count = 0;
			}
			
			if ( count == m ) {
				out++;
				count = 0;
			}
		}
		//return 1.0 / (count+1);
		return (xxx.size()/m) - out;
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

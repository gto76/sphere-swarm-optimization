package sso.foo;

import java.util.ArrayList;

import sso.functions.Function;
import sso.functions.cute.Hs110;
import sso.functions.cute.tooEasy.Himmelp1;
import _._;

public class Hs110Test {
	
	public static void main(String[] args) {

/*
		x(1) = 9.35027
		x(2) =  9.35027
		x(3) =  9.35027
		x(4) =  9.35027
		x(5) =  9.35027
		x(6) =  9.35027
		x(7) =  9.35027
		x(8) =  9.35027
		x(9) =  9.35027
		x(10) =  9.35027
		obj =  -45.7785
		 */
		ArrayList<Double> resitev = new ArrayList<Double>();
		for (int i = 0; i < 10; i++) {
			resitev.add(9.35027);
		}
		
		Function en = new Hs110();
		double res = en.calculate(resitev);
		_.p(""+res);
		
		
	}
}

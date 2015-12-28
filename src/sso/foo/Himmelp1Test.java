package sso.foo;

import java.util.ArrayList;

import _._;

import sso.functions.Function;
import sso.functions.cute.Sineali;
import sso.functions.cute.tooEasy.Himmelp1;

public class Himmelp1Test {

	public static void main(String[] args) {
		/*x(1) =    81.1916340065
		x(2) =    69.1579052108
		obj =   -62.0538693754*/
		ArrayList<Double> resitev = new ArrayList<Double>();
		
		resitev.add(-0.5207137);
		resitev.add(		-1.300132);
		resitev.add(				0.1197267);
		resitev.add(				-1.555699);
		resitev.add(				0.8491583);
		resitev.add(		-0.8498714);
		resitev.add(-0.8484271);
		resitev.add(-0.8510223);
		resitev.add(		-0.8465226);
		resitev.add(	-0.8542197);
		resitev.add(	-0.8410879);
resitev.add(		-0.8633767);
resitev.add(-0.8253715);
resitev.add(-0.8895582);
resitev.add(-0.7794883);
resitev.add(-0.9631884);
resitev.add(-0.6430701);
resitev.add(-1.157259);
resitev.add(-0.2315647);
resitev.add(-1.517240);
		
		Function en = new Sineali();
		double res = en.calculate(resitev);
		_.p(""+res);
	}
}

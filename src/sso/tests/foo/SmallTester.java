package sso.tests.foo;


import java.util.ArrayList;

import sso.SphereSwarmOptimization;
import sso.functions.standard.Schwefel;
import sso.io.Parameters;
import sso.io.Problem;
import sso.io.Razpon;
import sso.io.Result;


public class SmallTester {

	public static void main(String[] args) {
		
		double ohlajanje = 1.0;
		double zeloBlizu = 0.00001;
		int stParticlov = 200;
		int stIteracij = 1000;

/*
		double fHitrost = 2.12;
		double fPersonal = 1.59;
		double fGlobal = 2.95;
*/
		double fHitrost = 13;
		double fPersonal = -15;
		double fGlobal = 29;
		
		ArrayList<Razpon> razponi = Razpon.getRazponi(-500, 500, 5);
		
		Problem pr = new Problem( razponi, false, new Schwefel() );
		Parameters pa = new Parameters(ohlajanje, zeloBlizu, stParticlov,
				stIteracij, fHitrost, fPersonal, fGlobal );
		
		SphereSwarmOptimization sso = new SphereSwarmOptimization(false, false);
		Result res = sso.run(pr, pa);

		System.out.println(res.toString());
		System.out.println("Koncna hitrost: "+sso.getHitrost());
		
	}
}

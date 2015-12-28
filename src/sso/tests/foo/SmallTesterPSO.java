package sso.tests.foo;


import java.util.ArrayList;

import sso.Globals;
import sso.PSO;
import sso.SphereSwarmOptimization;
import sso.functions.standard.Schwefel;
import sso.io.Parameters;
import sso.io.Problem;
import sso.io.Razpon;
import sso.io.Result;
import sso.pso.random.MersenneTwister;


public class SmallTesterPSO {

	public static void main(String[] args) {
		
		double ohlajanje = 1.0;
		double zeloBlizu = 0.00001;
		int stParticlov = 300;
		int stIteracij = 1000;

/*
		double fHitrost = 2.12;
		double fPersonal = 1.59;
		double fGlobal = 2.95;
*/
		double fHitrost = 1;
		double fPersonal = 1;
		double fGlobal = 2;
		
		ArrayList<Razpon> razponi = Razpon.getRazponi(-500, 500, 5);
		
		Problem pr = new Problem( razponi, false, new Schwefel() );
		Parameters pa = new Parameters(ohlajanje, zeloBlizu, stParticlov,
				stIteracij, fHitrost, fPersonal, fGlobal );
		

		Globals.random = new MersenneTwister();
		
		PSO pso = new PSO(false, false);
		Result res = pso.run(pr, pa);

		System.out.println(res.toString());
		//System.out.println("Koncna hitrost: "+pso.getHitrost());
		
	}
}

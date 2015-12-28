package sso.tests.foo;

import java.util.ArrayList;

import _._;

import sso.Globals;
import sso.Optimizer;
import sso.PSO;
import sso.SphereSwarmOptimization;
import sso.functions.Function;
import sso.functions.cute.*;
import sso.io.Parameters;
import sso.io.Problem;
import sso.io.Razpon;
import sso.io.Result;
import sso.pso.random.MersenneTwister;

public class CuteTest {

	public static void main(String[] args) {
		
		Globals.random = new MersenneTwister();
		
		double zeloBlizu = 0.00001;
		int stParticlov = 60; //200
		int stIteracij = 60; //1000


		double fHitrost = 5.429573928825281;
		double fGlobal = 6.2459561356768205;
		double fPersonal = 5.897547096686111;
		double ohlajanje = 1.925797237180369;
		/*
		double ohlajanje = 3.0;		
		double fHitrost = 2.12;
		double fPersonal = 1.59;
		double fGlobal = 2.95;
		 */
		/*
		double fHitrost = 13;
		double fPersonal = -15;
		double fGlobal = 29;
		*/
		Function func = new Sineali();
		
		ArrayList<Razpon> razponi = func.getDefaultRazponi();
	
		Problem pr = new Problem( razponi, false, func );
		Parameters pa = new Parameters(ohlajanje, zeloBlizu, stParticlov,
				stIteracij, fHitrost, fPersonal, fGlobal );
		
		Parameters pa2 = new Parameters(ohlajanje, zeloBlizu, 60, stIteracij,
				0.6571, 1.6319, 0.6239 );
		
		_.p("____START\n\n");
		
		SphereSwarmOptimization sso = new SphereSwarmOptimization(false, false);
		test(sso,pr,pa);
		
		PSO pso = new PSO(false, false);
		test(pso,pr,pa2);
		
		_.p("____END\n");
		
	}

	public static void test(Optimizer opt, Problem pr, Parameters pa) {
		Result res = opt.run(pr, pa);
		System.out.println(opt.getName()+": "+res.toString());
		double razlika = res.fx - pr.enacba.getBestPossibleResult();
		_.p("Ralika: "+razlika+"\n\n");
	}
}

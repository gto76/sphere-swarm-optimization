package sso.tests;

import java.util.ArrayList;

import _._;

import sso.Globals;
import sso.Optimizer;
import sso.PSO;
import sso.SphereSwarmOptimization;
import sso.functions.meta.*;
import sso.gauss.GSphereSwarmOptimization;
import sso.io.Parameters;
import sso.io.Problem;
import sso.io.Razpon;
import sso.io.Result;
import sso.pso.random.MersenneTwister;

public class ParameterOptimizer {

	public static void main(String[] args) {
		Globals.random = new MersenneTwister();

		//optimize(new FMetaPSO());
		optimize(new FMetaSSO());

	}
	
	
	private static void optimize(FMeta optimizerTestFun) {
			
		_.p("optimize "+ optimizerTestFun.getOptimizerName()
				+ "=======================================\n");
		double zelo_blizu = 0.00001;
		int stParticlov = 60; //200
		int stIteracij = 60; //1000

		double MfHitrost = 5.429573928825281;
		double MfGlobal = 6.2459561356768205;
		double MfPersonal = 5.897547096686111;
		double ohlajanje = 1.925797237180369;
		//###########################//
		
		ArrayList<Razpon> razponi = optimizerTestFun.getDefaultRazponi();
		Problem metaProblem = new Problem( razponi, false, 
				optimizerTestFun);
		Parameters metaParameters = new Parameters( ohlajanje, zelo_blizu, 
				stParticlov, stIteracij, MfHitrost, MfPersonal, MfGlobal);
			
		//## RUN
		boolean printIterationResults = true;
		boolean animate = false;
		GSphereSwarmOptimization metaOptimizer = 
			new GSphereSwarmOptimization(printIterationResults, animate);
		Result metaRes = metaOptimizer.run(metaProblem, metaParameters);
		
		double fx = metaRes.fx;
		ArrayList<Double> xxx = metaRes.xxxPreslikani;

		//## OUTPUT
		System.out.println("fx = "+ fx);
		for ( int i = 0; i < xxx.size(); i++) {
			System.out.println("x" + (i+1) +" = "+ xxx.get(i));
		}
		System.out.println("===============================");
		System.out.println("zadnja hitrost: "+ metaOptimizer.getHitrost());
		System.out.println();
		
	}
	
}

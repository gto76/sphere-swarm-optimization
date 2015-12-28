package sso.tests;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import _._;

import sso.Globals;
import sso.MOL;
import sso.Optimizer;
import sso.PSO;
import sso.SphereSwarmOptimization;
import sso.functions.*;
import sso.functions.cute.*;
import sso.functions.standard.*;
import sso.functions.rest.*;
import sso.io.Parameters;
import sso.io.Problem;
import sso.io.Result;
import sso.pso.random.MersenneTwister;

public class Tester {

	static int tabNum = 14;

	
	public static void main(String[] args) {
		Globals.random = new MersenneTwister();
		
		int noOfRuns = 50;
					
		int stParticlov = 60;
		int stIteracij = 60;
		double zelo_blizu = 0.00001;
		double fHitrost;
		double fPersonal;
		double fGlobal;
		double ohlajanje;

		//##
		//## SSO
		//##
		/*
		fHitrost = 1.4354394303697644;
		fPersonal = 2;
		fGlobal = 1.4775254193549903;
		ohlajanje = 2.901687286795303;
		*/
		fHitrost = 1.2991175845938878;
		fPersonal = 2;
		fGlobal = 1.3682415229522156;
		ohlajanje = 3.2453842425665993;
		Parameters paramSSO = new Parameters(ohlajanje, zelo_blizu, 
				stParticlov, stIteracij, fHitrost, fPersonal, fGlobal);

		//##
		//## PSO
		//##
		//int stIzracunov = stParticlov * stIteracij;
		//stParticlov = 100;
		//stIteracij = (int)(stIzracunov / stParticlov);
		/*
		fHitrost = -0.6047392447044868; 
		fPersonal = -0.04891964801425619; 
		fGlobal = 1.3786730793849653;
		*/
		fHitrost = 0.4123807799016519 ; 
		fPersonal = 3.641952103377065; 
		fGlobal = 0.812469575826543;
		Parameters paramPSO = new Parameters(ohlajanje, zelo_blizu, 
				stParticlov, stIteracij, fHitrost, fPersonal, fGlobal);
		
		fHitrost = -0.3085;
		fGlobal = 2.0273;
		Parameters paramMOL = new Parameters(ohlajanje, zelo_blizu, 
				stParticlov, stIteracij, fHitrost, fPersonal, fGlobal);

		//##
		//## Problems
		//##
		ArrayList<Function> fff = new ArrayList<Function>();
		
		fff.add(new RosenbrockShifted());
		fff.add(new RastriginShifted());
		fff.add(new GriewankShifted());
		fff.add(new AckleyShifted());

		fff.add(new Schwefel());
		fff.add(new Rastrigin());
		fff.add(new Ackley());
		fff.add(new Griewank());
		fff.add(new FB1());
		fff.add(new FB2());
		fff.add(new FB3());
		fff.add(new Explin());
		fff.add(new Qudlin());
		fff.add(new Sineali());
		
		fff.add(new Branins());
		fff.add(new DropWave());
		fff.add(new Easom());
		fff.add(new FiftDeJung());
		fff.add(new Goldstein());
		fff.add(new Langerman());
		fff.add(new Michelawitz());
		fff.add(new Rosenbrock());
		fff.add(new Schubert());
		fff.add(new SixHumpCamelBack());
		List<Problem> problems = createTheProblems(fff);
			
		//##
		//## Test
		//##
		_.p("\t\t||		SSO			|		PSO			|	MOL		\n");
		_.pRepeatTab("=", tabNum);
		_.p("\n");
		
		Optimizer opt;
		double delta;
		LinkedHashMap<Problem, LinkedHashMap<Optimizer, Double>> fak = 
			new LinkedHashMap<Problem, LinkedHashMap<Optimizer, Double>>();
		
		Optimizer sso = new SphereSwarmOptimization(false, false);
		Optimizer pso = new PSO(false, false);
		Optimizer mol = new MOL(false,false);
		
		for ( Problem prob : problems ) {
			LinkedHashMap<Optimizer, Double> deltas = new LinkedHashMap<Optimizer, Double>();
			
			Function fun = prob.enacba;
			_.pTab(fun.toString(), 2);
			_.p("||");
			
			//opt = new SphereSwarmOptimization(false, false);
			delta = testOptimizerWithProblem(sso, prob, paramSSO, noOfRuns);
			deltas.put(sso, delta);
			
			//opt = new PSO(false, false);
			delta = testOptimizerWithProblem(pso, prob, paramPSO, noOfRuns);
			deltas.put(pso, delta);
			
			delta = testOptimizerWithProblem(mol, prob, paramMOL, noOfRuns);
			deltas.put(mol, delta);
			
			_.p("\n");
			
			double avgDelta = calculateAvgDelta(deltas.values());
			LinkedHashMap<Optimizer, Double> relativeScore = new LinkedHashMap<Optimizer, Double>();
			
			_.p("\t\t||");
			for ( Entry<Optimizer,Double> del : deltas.entrySet() ) {
				Optimizer optim = del.getKey();
				Double de = del.getValue();
				double rScore = de / avgDelta;
				relativeScore.put(optim, rScore);
				_.p("\t"+rScore+"\t\t|");
			}
			_.pn(); _.pRepeatTab("-", tabNum); _.pn();
			fak.put(prob, relativeScore);
			//System.out.println();	
		}	
		
		printSum(fak);
	
	}
	
	
	
	/**
	 * Naredi nov Optimizer-Double map v katerega pristeva kazni od vsakega problema.
	 * Na koncu jih zdeli s stevilom problemov in jih izpise 
	 */
	private static void printSum(LinkedHashMap<Problem, LinkedHashMap<Optimizer, Double>> fak) {
		_.pRepeatTab("-", tabNum); _.pn();
		_.pTab("SUM", 2);
		_.p("||");
		
		boolean ft = true;
		LinkedHashMap<Optimizer, Double> sum = new LinkedHashMap<Optimizer, Double>();
		for ( Problem p : fak.keySet() ) {
			
			LinkedHashMap<Optimizer, Double> scores = fak.get(p);
			for ( Optimizer op : scores.keySet() ) {
				if (ft) {
					sum.put(op, scores.get(op));
				}
				else {
					sum.put(op, sum.get(op)+scores.get(op));
				}
			}
			ft = false;
		}
		
		for (Optimizer op : sum.keySet()) {
			double avg = sum.get(op) / fak.size();
			sum.put(op, avg);
			_.p("\t"); _.pTab(""+avg, 3); _.p("\t|");
		}
		
	}
	
	private static double calculateAvgDelta(Collection<Double> ddd) {
		double sum = 0;
		for ( Double d : ddd ) {
			sum += d;
		}
		return sum / ddd.size();
	}
	
	private static double testOptimizerWithProblem(Optimizer opt, Problem prob, Parameters param,
			int noOfRuns) {
		Function fun = prob.enacba;
		double mean = testOptimizer(prob, param, opt, noOfRuns);
		double delta = mean - fun.getBestPossibleResult();
		_.p("\t"); _.pTab(""+delta, 3); _.p("\t|");
		return delta;
	}
	
	/**
	 * Creates List of Problems from List of Functions.
	 */
	private static List<Problem> createTheProblems(List<Function> fff) {
		List<Problem> ppp = new ArrayList<Problem>();
		for (Function f : fff) {
			ppp.add(new Problem (f.getDefaultRazponi(), false, f));
		}
		return ppp;
	}

	
	//##
	//## Run
	//##
	private static double testOptimizer(Problem prob, Parameters param, Optimizer opt, int runs) {
		long t1 = System.currentTimeMillis();
		MeanAndMin mm = getMeanAndMin(prob, param, runs, opt);
		long t2 = System.currentTimeMillis();
		/*
		System.out.println(opt.getName()+"\tPOVPRECJE PRI "+ prob.enacba.toString()+ ": " + mm.mean);
		System.out.println("\tNAJBOLJSI REZULTAT: " + mm.min);
		System.out.printf("Time usage: %.2f seconds\n",
				(double) (t2 - t1) / 1000);
		*/
		return mm.mean;
	}

	public static class MeanAndMin {
		final double mean;
		final double min;
		public MeanAndMin(double mean, double min) {
			this.mean = mean;
			this.min = min;
		}
	}
	
	private static MeanAndMin getMeanAndMin(Problem prob, Parameters param, 
				int runs, Optimizer opt ) {
		double sum = 0;
		double min = Double.MAX_VALUE;
		for (int i = 0; i < runs; i++) {
			Result res = opt.run(prob, param);
			sum += res.fx;
			if ( res.fx < min ) {
				min = res.fx;
			}
			//System.out.println("RUNNING: " + i);		
		}
		double mean = sum / (double)runs;
		MeanAndMin out = new MeanAndMin(mean, min);
		return out;
	}

}

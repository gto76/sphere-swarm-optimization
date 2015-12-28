// ------------------------------------------------------
// SwarmOps - Numeric and heuristic optimization for Java
// Copyright (C) 2003-2011 Magnus Erik Hvass Pedersen.
// Please see the file license.txt for license details.
// SwarmOps on the internet: http://www.Hvass-Labs.org/
// ------------------------------------------------------

package sso;

import java.util.ArrayList;

import sso.io.Parameters;
import sso.io.Problem;
import sso.io.Razpon;
import sso.io.Result;
import sso.pso.Tools;

/**
 * Particle Swarm Optimization (PSO) originally due to Eberhart et al. (1, 2).
 * This is a 'plain vanilla' variant which can have its parameters tuned (or
 * meta-optimized) to work well on a range of optimization problems. Generally,
 * however, the DE optimizer has been found to work better.
 * 
 * References:
 * 
 * (1) J. Kennedy and R. Eberhart. Particle swarm optimization. In Proceedings
 * of IEEE International Conference on Neural Networks, volume IV, pages
 * 1942-1948, Perth, Australia, 1995
 * 
 * (2) Y. Shi and R.C. Eberhart. A modified particle swarm optimizer. In
 * Proceedings of the IEEE International Conference on Evolutionary Computation,
 * pages 69-73, Anchorage, AK, USA, 1998.
 */
public class PSO extends Optimizer{

	private Problem problem;
		
	private static final double[] lowerBound = { 1.0, -2.0, -4.0, -4.0 };

	/** Upper boundary for the control parameters. */
	private static final double[] upperBound = { 300.0, 2.0, 4.0, 6.0 };

	/**
	 * Construct the object.
	 */
	public PSO(boolean print, boolean animate) {
		super(print, animate);
	}
	
	public String getName() {
		return "PSO";
	}

	public Result run(Problem problem, Parameters parameters) {
		
		this.problem = problem;
		
		// Retrieve parameter specific to PSO method.
		int numAgents = parameters.steviloParticlov;
		double omega = parameters.fHitrost;
		double phiP = parameters.fPersonal; // phi1
		double phiG = parameters.fGlobal; // phi2

		assert numAgents >= 1;

		// Get problem-context.
		int stEl = problem.steviloSpremenljivk;
		double[] lowerBound = new double[stEl];
		double[] upperBound = new double[stEl];
		for (int i = 0; i < stEl; i++) {
			Razpon razpon = problem.razponi.get(i);
			lowerBound[i] = razpon.from;
			upperBound[i] = razpon.to;
		}
		
		double[] lowerInit = lowerBound;
		double[] upperInit = upperBound;
		int n = problem.steviloSpremenljivk;

		// Allocate agent positions and associated fitness.
		double[][] agents = new double[numAgents][n];
		double[][] velocities = new double[numAgents][n];
		double[][] bestAgentPosition = new double[numAgents][n];
		double[] bestAgentFitness = new double[numAgents];
		boolean[] bestAgentFeasible = new boolean[numAgents];

		// Allocate velocity boundaries.
		double[] velocityLowerBound = new double[n];
		double[] velocityUpperBound = new double[n];

		// Iteration variables.
		int i, j, k;

		// Best-found position and fitness.
		double[] g = null;
		double gFitness = Double.MAX_VALUE;
		boolean gFeasible = false;

		// Initialize velocity boundaries.
		for (k = 0; k < n; k++) {
			double range = Math.abs(upperBound[k] - lowerBound[k]);

			velocityLowerBound[k] = -range;
			velocityUpperBound[k] = range;
		}

		// Initialize all agents.
		// This counts as iterations below.
		for (j = 0; j < numAgents; j++) {
			// Refer to the j'th agent as x and v.
			double[] x = agents[j];
			double[] v = velocities[j];

			// Initialize velocity.
			Tools.initializeUniform(v, velocityLowerBound, velocityUpperBound);

			// Initialize agent-position in search-space.
			Tools.initializeUniform(x, lowerInit, upperInit);

			// Enforce constraints and evaluate feasibility.
			bestAgentFeasible[j] = enforceConstraints(x);

			// Compute fitness of initial position.
			bestAgentFitness[j] = problem.enacba.calculate(convert(x));//problem.fitness(x, bestAgentFeasible[j]);
			
			
			// Initialize best known position.
			// Contents must be copied because the agent
			// will likely move to worse positions.
			Tools.copy(x, bestAgentPosition[j]);

			// Update swarm's best known position.
			// This must reference the agent's best-known
			// position because the current position changes.
			if (Tools.isBetterFeasibleFitness(gFeasible, bestAgentFeasible[j],
					gFitness, bestAgentFitness[j])) {
				g = bestAgentPosition[j];
				gFitness = bestAgentFitness[j];
				gFeasible = bestAgentFeasible[j];
			}

			// Trace fitness of best found solution.
			//trace(j, gFitness, gFeasible);
		}

		// Perform actual optimization iterations.
		for (i = 0; i < parameters.steviloIteracij; i++ ) {
			for (j = 0; j < numAgents; j++) {
				// Refer to the j'th agent as x and v.
				double[] x = agents[j];
				double[] v = velocities[j];
				double[] p = bestAgentPosition[j];

				// Pick random weights.
				double rP = Globals.random.nextUniform();
				double rG = Globals.random.nextUniform();

				// Update velocity.
				for (k = 0; k < n; k++) {
					v[k] = omega * v[k] + phiP * rP * (p[k] - x[k]) + phiG * rG
							* (g[k] - x[k]);
				}

				// Enforce velocity bounds before updating position.
				Tools.bound(v, velocityLowerBound, velocityUpperBound);

				// Update position.
				for (k = 0; k < n; k++) {
					x[k] = x[k] + v[k];
				}

				// Enforce constraints and evaluate feasibility.
				boolean newFeasible = enforceConstraints(x);

				// Compute fitness if feasibility is same or better.
				if (Tools.isBetterFeasible(bestAgentFeasible[j], newFeasible)) {
					// Compute new fitness.
					double newFitness =  problem.enacba.calculate(convert(x));//problem.fitness(x, bestAgentFeasible[j]);
					

					// Update best-known position in case of fitness
					// improvement.
					if (Tools.isBetterFeasibleFitness(bestAgentFeasible[j],
							newFeasible, bestAgentFitness[j], newFitness)) {
						// Update best-known position.
						// Contents must be copied because the agent
						// will likely move to worse positions.
						Tools.copy(x, bestAgentPosition[j]);
						bestAgentFitness[j] = newFitness;

						// Update feasibility.
						bestAgentFeasible[j] = newFeasible;

						// Update swarm's best known position,
						// if feasibility is same or better and fitness is an
						// improvement.
						// This must reference the agent's best-known
						// position because the current position changes.
						if (Tools.isBetterFeasibleFitness(gFeasible,
								bestAgentFeasible[j], gFitness,
								bestAgentFitness[j])) {
							g = bestAgentPosition[j];
							gFitness = bestAgentFitness[j];
							gFeasible = bestAgentFeasible[j];
						}
					}
				}

				// Trace fitness of best found solution.
				//trace(i, gFitness, gFeasible);
			}
		}

	
		// Return best-found solution and fitness.
		//return new Result(g, gFitness, gFeasible, i);
		Result result = new Result(gFitness, null, convert(g));
		return result;
	}
	
	public static ArrayList<Double> convert(double[] in) {
		ArrayList<Double> out = new ArrayList<Double>();
		for ( Double x : in ) {
			out.add(x);
		}
		return out;
	}
	
	public static double[] convert(ArrayList<Double> in) {
		int size = in.size();
		double[] out = new double[size];
		for (int i = 0; i < size; i++) {
			out[i] = in.get(i);
		}
		return out;
	}

	public double[] getLowerBoundOfEq() {
		ArrayList<Razpon> rrr = problem.razponi;
		int size = rrr.size();
		double[] out = new double[size];
		for (int i = 0; i < size; i++) {
			out[i] = rrr.get(i).from;
		}
		return out;
	}
	
	public double[] getUpperBoundOfEq() {
		ArrayList<Razpon> rrr = problem.razponi;
		int size = rrr.size();
		double[] out = new double[size];
		for (int i = 0; i < size; i++) {
			out[i] = rrr.get(i).to;
		}
		return out;
	}
	
	/**
	 * 
	 * FROM PROBLEM!!!
	 * 
	 */
	
	/**
	 * Enforce constraints and evaluate feasibility.
	 * 
	 * @param parameters
	 *            candidate solution.
	 */
	
	public boolean enforceConstraints(double[] parameters) {
		// If you do not wish to enforce constraints you should make
		// this call isFeasible().

		// By default we bound the candidate solution to the search-space
		// boundaries.
		Tools.bound(parameters, getLowerBoundOfEq(), getUpperBoundOfEq());

		// Since we know that candidate solution is now within bounds and this
		// is all that is required for feasibility, we could just return true
		// here. isFeasible() is called for educational purposes and because
		// most optimizers do not call isFeasible() but call
		// enforceConstraints() so if the user were to only override
		// isFeasible() constraint handling would not work as expected.
		return isFeasible(parameters);
	}

	/**
	 * Evaluate feasibility (constraint satisfaction).
	 * 
	 * @param parameters
	 *            candidate solution.
	 * @return
	 */

	public boolean isFeasible(double[] parameters) {
		return Tools.isBetweenBounds(parameters, getLowerBoundOfEq(),
				getUpperBoundOfEq());
	}



}

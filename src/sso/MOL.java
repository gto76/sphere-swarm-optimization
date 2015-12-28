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
 * Many Optimizing Liaisons (MOL) optimization method devised as a
 * simplification to the PSO method originally due to Eberhart et al. (1, 2).
 * The MOL method does not have any attraction to the particle's own best known
 * position, and the algorithm also makes use of random selection of which
 * particle to update instead of iterating over the entire swarm. It is similar
 * to the "Social Only" PSO suggested by Kennedy (3), and was studied more
 * thoroguhly by Pedersen et al. (4) who found it to sometimes outperform PSO,
 * and have more easily tunable control parameters.
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
 * 
 * (3) J. Kennedy. The particle swarm: social adaptation of knowledge, In:
 * Proceedings of the IEEE International Conference on Evolutionary Computation,
 * Indianapolis, USA, 1997.
 * 
 * (4) M.E.H. Pedersen and A.J. Chipperfield. Simplifying particle swarm
 * optimization. Applied Soft Computing, 10, p. 618-628, 2010.
 */
public class MOL extends Optimizer {

	private Problem problem;
	
	/**
	 * Construct the object.
	 */
	public MOL(boolean print, boolean animate) {
		super(print, animate);
	}

	@Override
	public String getName() {
		return "MOL";
	}

	@Override
	public Result run(Problem problem, Parameters parameters) {

		this.problem = problem;
		
		// Retrieve parameter specific to PSO method.
		int numAgents = parameters.steviloParticlov;
		double omega = parameters.fHitrost;
		double phi = parameters.fGlobal;

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

		
			// Best-found position, fitness and constraint feasibility.
			double[] g = new double[n];
			double gFitness = Double.MAX_VALUE;
			boolean gFeasible = false;

			// Iteration variables.
			int i, j, k;

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

				// Initialize agent-position in search-space.
				Tools.initializeUniform(x, lowerInit, upperInit);

				// Initialize velocity.
				Tools.initializeUniform(v, velocityLowerBound, velocityUpperBound);

				// Enforce constraints and evaluate feasibility.
				boolean newFeasible = enforceConstraints(x);


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
				
			}

			
			// Perform actual optimization iterations.
			// Perform actual optimization iterations.
			for (i = 0; i < parameters.steviloIteracij; i++ ) {
				for (j = 0; j < numAgents; j++) {
			
					// Pick random agent.
					j = Globals.random.nextIndex(numAgents);
	
					// Refer to the j'th agent as x and v.
					double[] x = agents[j];
					double[] v = velocities[j];
	
					// Pick random weight.
					double r = Globals.random.nextUniform();
	
					// Update velocity.
					for (k = 0; k < n; k++) {
						v[k] = omega * v[k] + phi * r * (g[k] - x[k]);
					}
	
					// Enforce velocity bounds before updating position.
					Tools.bound(v, velocityLowerBound, velocityUpperBound);
	
					// Update position.
					for (k = 0; k < n; k++) {
						x[k] = x[k] + v[k];
					}
	
					// Enforce constraints and evaluate feasibility.
					boolean newFeasible = enforceConstraints(x);
	
					// Compute fitness if feasibility (constraint satisfaction) is same
					// or better.
					if (Tools.isBetterFeasible(gFeasible, newFeasible)) {
						// Compute new fitness.
						double newFitness =  problem.enacba.calculate(convert(x));//problem.fitness(x, bestAgentFeasible[j]);
						
						// Update swarm's best known position, if improvement.
						if (Tools.isBetterFeasibleFitness(gFeasible, newFeasible,
								gFitness, newFitness)) {
							Tools.copy(x, g);
							gFitness = newFitness;
							gFeasible = newFeasible;
						}
					}
	
				}
			}
			
			// Return best-found solution and fitness.
			//return new Result(g, gFitness, gFeasible, i);
			Result result = new Result(gFitness, null, convert(g));
			return result;	
		
		
	}

	/** Names of the control parameters. */
	private static final String[] parameterName = { "S", "Omega", "Phi" };

	/** Lower boundary for the control parameters. */
	private static final double[] lowerBound = { 1.0, -2.0, -4.0 };

	/** Upper boundary for the control parameters. */
	private static final double[] upperBound = { 300.0, 2.0, 6.0 };

	
	/**
	 * Get control parameter, number of agents aka. swarm-size.
	 * 
	 * @param parameters
	 *            parameters passed to optimizer.
	 */
	public static int getNumAgents(double[] parameters) {
		return (int) Math.round(parameters[0]);
	}

	/**
	 * Get control parameter omega.
	 * 
	 * @param parameters
	 *            parameters passed to optimizer.
	 */
	public double getOmega(double[] parameters) {
		return parameters[1];
	}

	/**
	 * Get control parameter phi.
	 * 
	 * @param parameters
	 *            parameters passed to optimizer.
	 */
	public double getPhi(double[] parameters) {
		return parameters[2];
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
	@Override
	public boolean isFeasible(double[] parameters) {
		int numAgents = getNumAgents(parameters);
		double omega = getOmega(parameters);
		double phi = getPhi(parameters);

		// Example of constraints on an optimizer's control optimizers.
		// These particular constraints are only for demonstration purposes.
		return (numAgents >= 1) && (omega > 0 || omega < -0.5)
				&& (omega * phi < 0) && (omega + phi < 2);
	}
	*/

	public boolean isFeasible(double[] parameters) {
		return Tools.isBetweenBounds(parameters, getLowerBoundOfEq(),
				getUpperBoundOfEq());
	}

}

package sso;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import sso.graphics.FcbFrame;
import sso.io.Parameters;
import sso.io.Problem;
import sso.io.Result;

/**
 * Tries to find the best solution to the given optimization problem,
 * using particle swarm optimization variant in which particles
 * move inside closed space called n-sphere instead of euclidian space.
 * @author Jure
 *
 */
public class SphereSwarmOptimization extends Optimizer {
	
	//###
	
	private int noOfDim;
	private double hitrost;
	//double razpon;
	
	private FcbFrame frame;
	private ParticleContainer pc;
	private Result globalBest;
	
	
	//##
	//## Const:
	//##	
	public SphereSwarmOptimization(boolean print, boolean animate) {
		super(print, animate);
	}

	public String getName() {
		return "SSO";
	}
		
	public Result run(Problem problem, Parameters parameters) {
		
		noOfDim = problem.steviloSpremenljivk + 1;
		hitrost = parameters.ZACETNA_HITROST;
		//razpon = in.max - in.min;

		//Global Best Init:
		ArrayList<Double> globalBestPosition = new ArrayList<Double>(noOfDim);
		ArrayList<Double> globalBestPositionPres = new ArrayList<Double>(noOfDim-1);
		double globalBestScore;
		if ( problem.MAX ) globalBestScore = -Double.MAX_VALUE;
		else globalBestScore = Double.MAX_VALUE;
		globalBest = new Result(globalBestScore, globalBestPosition, globalBestPositionPres);
		
		
		pc = new ParticleContainer(problem, parameters, globalBest);
	
		if ( ANIMATE ) {
			// Create application frame.
			frame = new FcbFrame(pc, REFRESH_RATE);

			frame.setSize(500, 500);
			//frame.setExtendedState(frame.getExtendedState()|JFrame.MAXIMIZED_BOTH);
			// Show frame.
			frame.show();
		}

		
		//##
		//## MAIN LOOP START
		//##
		for ( int iter = 0; iter < parameters.steviloIteracij; iter++ ) {
			
			if ( POTRDILO ) {
				try {
					System.in.read();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			for ( int i = 0; i < parameters.steviloParticlov; i++ ) {
				pc.particles.get(i).calculateScore();
				//System.out.print(" kalkuliram skore " + i); 
				//System.out.print(" " + Particle.particles.get(i).pn.toString()); 
			}
			
			//System.out.println();
			//System.out.println("KONEC IZPISA ZAC. POZICIJ");
			
			for ( int i = 0; i < parameters.steviloParticlov; i++ ) {
				pc.particles.get(i).premakni(hitrost);
				//System.out.print(" premikam " + i); 
			}
			
			
			double konstanta = Math.pow(parameters.KONCNA_HITROST, (1/parameters.FAKTOR_OHLAJANJA))
								/ parameters.steviloIteracij;
			hitrost = Math.pow(iter * konstanta, parameters.FAKTOR_OHLAJANJA);
			
			if (PRINT) {
				System.out.println("Koncana Iteracija "+ iter +". Hitrost: " +
							hitrost +". Global Best fx: "+ globalBest.fx +
							". Global Best xxx: " + globalBest.xxxPreslikani.toString()); 
			}
		}
		//##
		//##MAIN LOOP END
		//##
		
			/*new ArrayList<Double>(size);
		for ( int i = 0; i < size; i++ ) {
			double x = Particle.preslikaj(Particle.globalBestPosition.get(i));
			preslikaniXi.add(x);
		}*/
		
		Result rezultat = pc.getGlobaBest();
		return rezultat;
		
	}
	
	public double getHitrost() {
		return hitrost;
	}
	
}

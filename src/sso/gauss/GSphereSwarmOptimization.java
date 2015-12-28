package sso.gauss;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sso.Particle;
import sso.ParticleContainer;
import sso.graphics.FcbFrame;
import sso.graphics.GFcbFrame;
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
public class GSphereSwarmOptimization {
	
	private final double RADIUS = 0.3;
	
	private final boolean ANIMATE;
	private final int REFRESH_RATE = 1000; //za animacijo
	private final boolean POTRDILO = false;
	private final boolean PRINT;
	
	//###
	
	private int noOfDim;
	private double hitrost;
	
	private GParticleContainer pc;
	private GFcbFrame frame;
	
		
	//##
	//## Const:
	//##	
	public GSphereSwarmOptimization(boolean print, boolean animate) {
		this.PRINT = print;
		this.ANIMATE = animate;
	}
		
	public Result run(Problem problem, Parameters parameters) {
		
		System.out.println("__________________GSphereSearm.../run: START, " +
				"new GParticle Container");
		
		noOfDim = problem.steviloSpremenljivk + 1;
		hitrost = parameters.ZACETNA_HITROST;
				
		pc = new GParticleContainer(problem, parameters, RADIUS);
		
		System.out.println("__________________GSphereSearm.../run: " +
				"findGlobalBest(0)");
		findGlobalBest(0);
			
		if ( ANIMATE ) {
			frame = new GFcbFrame(pc, REFRESH_RATE);
			frame.setSize(1200, 700);
			frame.show();
		}
		 
					
							//#####################//
							//## MAIN LOOP START ##//
							//#####################//
				
		System.out.println("__________________GSphereSearm.../run: MAIN LOOP START");
		for ( int iter = 0; iter < parameters.steviloIteracij; iter++ ) {
			if ( POTRDILO ) {
				try {
					System.in.read();
				} catch (IOException e) {e.printStackTrace();}
			}
			
			Point.increaseNoOfGenerations();
						
			for ( int i = 0; i < parameters.steviloParticlov; i++ ) {
				pc.particles.get(i).premakni(hitrost);
			}		
			
			for ( int i = 0; i < parameters.steviloParticlov; i++ ) {			
				pc.particles.get(i).findPersonalBest();
			}
			
			findGlobalBest(iter);
			
			double konstanta = Math.pow(parameters.KONCNA_HITROST, (1/parameters.FAKTOR_OHLAJANJA))
								/ parameters.steviloIteracij;
			hitrost = Math.pow(iter * konstanta, parameters.FAKTOR_OHLAJANJA);
			
			if (PRINT) {
				String hit = String.format("%.2f", hitrost);
				System.out.print("\nKoncana Iteracija "+ (iter+1) +".  Hitrost: " +
							hit);// +". Global Best: "+ pc.globalBestPoint.p.toString());
				povpreciVseTocke();
				System.out.println("Best point: " +getNoOfGlobalBest(1).toString());
				//System.out.println("10 worst: " +getNoOfGlobalWorst(1).toString());
			}

			if ( ANIMATE && ((iter % 5) == 0) ) {
				frame.putImageInFrame(frame.getFrame(), frame.createImage());
			}
		}
							//###################//
							//## MAIN LOOP END ##//
							//###################//
									
			
		Point point = pc.getGlobalBest().p;
		Result rezultat = new Result(point.getAverageFitnes(), point.getOriginalPosition(), point.getDenormalisedPosition());
		return rezultat;
		
	}

	public double getHitrost() {
		return hitrost;
	}
	
	public void povpreciVseTocke() {
		long stTock = 0;
		double sum = 0;
		for (Point p : pc.listOfPoints) {
			sum += p.calculateAverageFitnes();
			stTock++;
		}
		double avg = sum / stTock;
		String avgS = String.format ("%.2f", avg);
		System.out.println("  stTock: " + stTock + "  avgFit: " + avgS);
	}
	
	public void findGlobalBest(int iter) {
		
		/**
		 * to je cisto zacasna resitev,
		 * problem je v global minimumih ki se nakako zagozdijo na robu ene dimenzije
		 * in ne zaznavajo vec sosedov razen samih sebe -> se pravi dobijo novega soseda
		 * vsako iteracijo...
		 */
		/*
		if(iter > 3) { iter += (iter/2); }
		else if (iter > 10) { iter += iter; }
		else if (iter > 30) { iter *= 4; }
		*/
		/*
		Point min;
		int i = 0;
		do {
			min = pc.listOfPoints.get(i);
		} while(min.getNoOfNeighbours()<iter);
		*/

		Point min = pc.listOfPoints.get(0);
		
		for (Point p : pc.listOfPoints) {
			//if (p.getNoOfNeighbours() > iter) {
				if (p.getAverageFitnes() < min.getAverageFitnes()) {
					min = p;
				}
			//}
		}
		pc.globalBestPoint.p = min;
		//System.out.println("Global best ima sosedov: " + min.getNoOfNeighbours());
	}


	public List<Point> getNoOfGlobalBest(int no) {
		return getNoOfGlobalExt(no, true);
	}

	public List<Point> getNoOfGlobalWorst(int no) {
		return getNoOfGlobalExt(no, false);
	}
	
	private List<Point> getNoOfGlobalExt(int no, boolean max) {
		ArrayList<Point> out = new ArrayList<Point>(no);
		for (Point p : pc.listOfPoints) {
			int i;
			if (out.size() > no) i = no-1;
			else i = out.size()-1;
			if (max) {
				for (; (i >= 0) && (p.getAverageFitnes() < out.get(i).getAverageFitnes()); i--);
			} else {
				for (; (i >= 0) && (p.getAverageFitnes() > out.get(i).getAverageFitnes()); i--);
			}
			i++;
			if (i <= no-1) out.add(i, p);
		}
		return out.subList(0, no);
	}
		
}

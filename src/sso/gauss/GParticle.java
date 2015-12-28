package sso.gauss;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import sso.io.Parameters;
import sso.io.Problem;

/**
 * Particle used by the sphere swarm optimization class.
 * Knows how to move by itself, only thing it neads is
 * a global best position object.
 * @author Jure
 * 
 * normalizirane in originalne tocke imajo vrednosti dimenzij od -1 do 1!!!!!
 * s tem da ima lahko max vrednost(-1/1) pri originalih samo ena tocka!!!!
 *
 */
public class GParticle {
	
	private final List<Point> listOfPoints;	
	double radius;
	
	GParticleContainer gpc;
	
	public int noOfDim;
	Problem prob;
	Parameters param;
	public PointContainer globalBest;
	public boolean IZPIS;
	
	public Point oldPoint; //old position
	public Point oldOldPoint; //old old position
	
	Point personalBestPoint;
	
	//## Shranjene vse pozicije dozdej, skupi z fitnessi:
	HashSet<Point> history;
	
	
	/**
	 * Const
	 */
	public GParticle(int noOfDim, Problem prob, Parameters param, PointContainer globalBest, boolean IZPIS, GParticleContainer gpc, List<Point> listOfPoints, double radius) {
		
		this.radius = radius;
		this.listOfPoints = listOfPoints;
		
		this.gpc = gpc;
		
		history = new HashSet<Point>();
		
		this.noOfDim = noOfDim;
		this.prob = prob;
		this.param = param;
		this.globalBest = globalBest;
		this.IZPIS = IZPIS;
			
		ArrayList<Double> tempNewPosition = getRandomPosition(noOfDim);
		ArrayList<Double> tempOldPosition = getRandomPosition(noOfDim);
		
		oldPoint = new Point(tempNewPosition, radius, prob, listOfPoints);
		oldOldPoint = new Point(tempOldPosition, radius, prob, listOfPoints);
		
		history.add(oldPoint);
		history.add(oldOldPoint);
		
	}
	
	private static ArrayList<Double> getRandomPosition(int dimensions) {
		ArrayList<Double> out = new ArrayList<Double>(dimensions);
		for ( int i = 0; i < dimensions; i++) {
			double pnTemp = (Math.random() - 0.5);
			out.add( pnTemp );
		}
		correct(out);
		return out;
	}
	
	/**
	 * Premakne delec na novo pozicijo
	 * Uplivajo:
	 * hitrost (stara pozicija)
	 * personal best
	 * global best
	 * globalna hitrost
	 */
	public void premakni(double hitrost) {
		double r;	
		ArrayList<Double> newPosition = oldPoint.getOriginalPosition();
	
		//stara pozicija
		//TODO: to ni isto kot hitrost!!! I GUES :)
		if ( oldOldPoint != null) {
			r = 1;
			for(int i = 0; i < noOfDim; i++)  // in each dimension..
			{
				r -= oldOldPoint.getOriginalPosition().get(i) // calculate the resulting distance
				* oldPoint.getOriginalPosition().get(i);
			}
			r = Math.sqrt(2*r) * (hitrost);	
	
			if ( r != Double.NaN && r > param.ZELO_BLIZU ) {
			
				for(int i = 0; i < noOfDim; i++) { // in each dimension..
					
					double r2 = ( oldOldPoint.getOriginalPosition().get(i) - oldPoint.getOriginalPosition().get(i) ) / r;
					
					r2 = r2 * param.fHitrost;
					
					double position = newPosition.get(i);
					newPosition.set( i, position - r2 );
				
				}	
			}
		}
	
		//personal Best:
		if ( personalBestPoint != null ) {
			r = 1;
			for(int i = 0; i < noOfDim; i++)  // in each dimension..
			{
				r -= personalBestPoint.getOriginalPosition().get(i) // calculate the resulting distance
				* oldPoint.getOriginalPosition().get(i);
			}
			r = Math.sqrt(2*r) * hitrost;	
				
			if ( r != Double.NaN && r > param.ZELO_BLIZU ) { //ININININ
				
				for(int i = 0; i < noOfDim; i++) { // in each dimension..
					
					double r2 = ( personalBestPoint.getOriginalPosition().get(i) - oldPoint.getOriginalPosition().get(i) ) / r;
					
					r2 = r2 * param.fPersonal;
					
					double position = newPosition.get(i);
					newPosition.set( i, position + r2 );
				
				}
			}
		}
		
		//global Best:
		if ( globalBest.p != null ) {
			r = 1;
			//System.out.println("NO of dim: "+noOfDim);
			for(int i = 0; i < noOfDim; i++)  // in each dimension..
			{
				r -= globalBest.p.getOriginalPosition().get(i) // calculate the resulting distance
				* oldPoint.getOriginalPosition().get(i);
			}
			r = Math.sqrt(2*r) * hitrost;	
	
			if ( r != Double.NaN && r > param.ZELO_BLIZU ) { //ININININ
				
				for(int i = 0; i < noOfDim; i++) { // in each dimension..
					
					double r2 = ( globalBest.p.getOriginalPosition().get(i) - oldPoint.getOriginalPosition().get(i) ) / r;
					
					r2 = r2 * param.fGlobal;
					
					double position = newPosition.get(i);
					newPosition.set( i, position + r2 );
				
				}
			}
		}	
		
		//spravi v staro pozicijo
		/*
		for(int i = 0; i < noOfDim; i++) { // in each dimension..
			double position = oldPoint.originalPosition.get(i);
			oldOldPoint.originalPosition.set( i, position );
		}
		*/
		oldOldPoint = oldPoint;
		
		correct(newPosition);	// Correct movements into spere with radius 1 and update them
		oldPoint = new Point(newPosition, radius, prob, listOfPoints);
		history.add(oldPoint);
		//clculateScore();
		
	}

		
	public void findPersonalBest() {
		Point min = oldPoint;
		for (Point p : history) {
			if (p.getAverageFitnes() < min.getAverageFitnes()) {
				min = p;
			}
		}
		personalBestPoint = min;
	}
	
	
	public static double dobiRazdaljo(ArrayList<Double> in) {
		double out = 0;
		for ( double os : in ) {
			out += Math.pow(os, 2);
		}
		return Math.sqrt(out);
	}
	
	/**
	 * Correct data within radius 1 sphere and update model
	 * @param in: particle
	 */
	public static void correct(ArrayList<Double> in)
	{
		double r = 0;
		
		for( int j=0; j < in.size() ; j++)
		{
			r += Math.pow( in.get(j), 2 );
		}
		
		r = Math.sqrt(r);
		
		for( int j=0; j < in.size() ; j++)
		{
		
			double pnTemp = in.get(j);
			pnTemp /= r;
			in.set(j, pnTemp );

		}
	
	}
	
	/**
	 * Correct data within radius 1 CUBE and update model.
	 * @param in
	 */
	public static void correctKocka(ArrayList<Double> in)
	{	
		//najdi dominantno os
		int domIndex = 0;
		double domAbs = Math.abs(in.get(0));
		
		for( int i = 1; i < in.size(); i++ ) {
			if ( Math.abs(in.get(i)) > domAbs ) {
				domAbs = Math.abs(in.get(i));
				domIndex = i;
			}
		}
		
		double dom = in.get(domIndex);
		
		//izracunaj nove vrednosti
		for( int i = 0; i < in.size(); i++ ) {
			if ( i != domIndex ) {
				double temp = in.get(i);
				temp = temp / in.get(domIndex);
				in.set(i, temp);
			}
		}
		
		//izracunaj novo vrednost za dom
		if ( in.get(domIndex) > 0.0 ) {
			in.set(domIndex, 1.0);
		}
		else {
			in.set(domIndex, -1.0);
		}
		
	}

	public boolean isBetter( double a, double b) {
		if ( prob.MAX ) {
			if ( a > b ) {return true;}
			else {return false;}
		}
		else {
			if ( a < b ) {return true;}
			else {return false;}
		}	
	}
	
		
}

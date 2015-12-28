package sso.gauss;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import _.IntegerWrapper;

import sso.io.Parameters;
import sso.io.PointWithDistance;
import sso.io.Problem;
import sso.io.Result;


public class GParticleContainer {

	public final ArrayList<Point> listOfPoints;	
	
	private boolean IZPIS = false;
	public double RADIUS;
	
	private final int noOfDim;
	public final ArrayList<GParticle> particles;
	//private Result globalBest;
	//public Point globalBestPoint;
	public PointContainer globalBestPoint;
	
	private Problem prob;
	
	//public static final double normalConstant = 1/Math.sqrt(2*Math.PI);
	
	//##
	//## Cons:
	//##
	public GParticleContainer(Problem prob, Parameters param, double RADIUS) {
		
		listOfPoints = new ArrayList<Point>();
		
		this.RADIUS = RADIUS;
		
		this.prob = prob;		
		globalBestPoint = new PointContainer();
		
		noOfDim = prob.steviloSpremenljivk + 1;
		//this.globalBest = globalBest;
		
		particles = new ArrayList<GParticle>(param.steviloParticlov);
		
		for ( int i = 0; i < param.steviloParticlov; i++ ) {
			GParticle novi = new GParticle(noOfDim, prob, param, globalBestPoint, 
					IZPIS, this, listOfPoints, RADIUS);
			particles.add(novi);
		}
		//particles.get(0).createNewPoint();
		//globalBestPoint.p = particles.get(0).newPoint;
	}

	//###
	//### Fitnes Mean Calculation START
	//###

	/**
	 * gets mean of fitnes in passed location.
	 * Every point closer than radius contributes it's share.
	 * Closer points contribut more
	 */
	public double getConeFitnesOfLocation(ArrayList<Double> location, 
			double radius) {
		return getFitnesOfLocation(listOfPoints, location, radius, true, 
				Integer.MAX_VALUE, null);
	}
	public double getConeFitnesOfLocation(ArrayList<Double> location, 
			double radius, int nMax) {
		return getFitnesOfLocation(listOfPoints, location, radius, true, 
				nMax, null);
	}
	public double getNormalFitnesOfLocation(ArrayList<Double> location, double radius) {
		return getFitnesOfLocation(listOfPoints, location, radius, false, 
				Integer.MAX_VALUE, null);
	}
	public double getNormalFitnesOfLocation(ArrayList<Double> location, 
			double radius, int nMax) {
		return getFitnesOfLocation(listOfPoints, location, radius, false, 
				nMax, null);
	}
	
	/**
	 * @param nMax Maximum number of neighbours that may contribute to mean.
	 * @return
	 */
	public static double getFitnesOfLocation(List<Point> ppp,
			ArrayList<Double> location,	double radius, boolean cone, int nMax, 
			IntegerWrapper isSetToNoOfNeighbours) {
		
		List<PointWithDistance> pdpdpd = new ArrayList<PointWithDistance>();

		for (Point p : ppp) {
			double distance = p.getNormalisedDistance(location);
			if (distance < radius) {
				pdpdpd.add(new PointWithDistance(p, distance));
			}
		}
		int size = pdpdpd.size();
		isSetToNoOfNeighbours.set(size);
		
		if (size <= nMax) {
			return getFitnesOfLocation2(pdpdpd, radius, cone);
		}
		else {
			Collections.sort(pdpdpd);
			pdpdpd = pdpdpd.subList(0, nMax);
			return getFitnesOfLocation2(pdpdpd, radius, cone);
		}
	}
	
	private static double getFitnesOfLocation2(List<PointWithDistance> pdpdpd, 
			double radius, 
			boolean cone) {
		double deljenec = 0; 
		double delitelj = 0;
		
		for (PointWithDistance pd : pdpdpd) {
			double k;
			if (cone) {
				k = (radius-pd.distance)/radius;
			} else {
				k = Math.pow(Math.E, -Math.pow((pd.distance/radius)*5.0, 2)/2.0);
				//standard normal distribution
			}
			deljenec += pd.point.getFitnes() * k;
			delitelj += k;
		}
		double averageFitnes = deljenec / delitelj;
		return averageFitnes;
	}
	
	//###
	//### Fitnes Mean Calculation END
	//###
	
	public int getNoOfDim() {
		return noOfDim;
	}

	public double getMaxFitnes() {
		double max = -Double.MAX_VALUE;
		for (Point p :listOfPoints) {
			if (p.getFitnes() > max) {
				max = p.getFitnes();
			}
		}
		return max;
	}
	
	public double getMinFitnes() {
		double min = Double.MAX_VALUE;
		for (Point p :listOfPoints) {
			if (p.getFitnes() < min) {
				min = p.getFitnes();
			}
		}
		return min;
	}
	
	public PointContainer getGlobalBest() {
		return globalBestPoint;
	}
	
}

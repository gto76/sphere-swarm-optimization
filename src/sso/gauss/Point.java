package sso.gauss;

import java.util.ArrayList;
import java.util.List;

import _.IntegerWrapper;

//import com.sun.xml.internal.bind.v2.runtime.RuntimeUtil.ToStringAdapter;

import sso.io.Problem;

public class Point {
	
	static private int noOfPoints = 0;
	static private int noOfGenerations = 0;
	
	private final int MAX_NEIGHBOURS = 50;
	
	private int pointId;
	private int generationId;
	
	private final ArrayList<Double> originalPosition;
	private final ArrayList<Double> normalisedPosition;
	private final double fitnes;
	private double averageFitnes;
	private final double radius;
	private final Problem prob;
	
	private final List<Point> listOfPoints;
	
	private int stSosedov = 0;
	

	//##
	//## Const
	//##
	public Point(ArrayList<Double> originalPosition, double radius, 
			Problem prob, List<Point> zbirka) {

		pointId = noOfPoints++;
		generationId = noOfGenerations;
		
		GParticle.correct(originalPosition);
		this.originalPosition = originalPosition;
		this.radius = radius;
		this.prob = prob;
		this.listOfPoints = zbirka;
		
		this.normalisedPosition = calculateNormalisedPosition();
		this.fitnes = calculateFitnes();
		this.averageFitnes = Double.MAX_VALUE;
		//calculateAverageFitnes();
		
		zbirka.add(this);
	}

	public static void increaseNoOfGenerations() {
		Point.noOfGenerations++;
	}
	
	//##
	//## GETERS:
	//##
	
	//##ID's
	public int getPointId() {
		return pointId;
	}

	public int getGenerationId() {
		return generationId;
	}
	
	//## Position:
	public ArrayList<Double> getOriginalPosition() {
		return new ArrayList<Double>(originalPosition);
	}

	public ArrayList<Double> getNormalisedPosition() {
		return new ArrayList<Double>(normalisedPosition);
	}
	
	public ArrayList<Double> getDenormalisedPosition() {
		ArrayList<Double> denormaliziraniXi = new ArrayList<Double>(normalisedPosition.size());
				
		for ( int i = 0; i < normalisedPosition.size(); i++ ) {
			double a = normalisedPosition.get(i);
			double x = preslikajDimenzijo(a, prob.razponi.get(i).from, prob.razponi.get(i).to);
			denormaliziraniXi.add(x);
		}
		return denormaliziraniXi;
	}

	//## Distance
	public double getNormalisedDistance(Point p) {
		double sum = 0;
		for (int i = 0; i < this.normalisedPosition.size(); i++) {
			double x1 = this.normalisedPosition.get(i);
			double x2 = p.normalisedPosition.get(i);
			sum += Math.pow(x1 - x2, 2);
		}
		return Math.sqrt(sum);		
	}

	//## Distance
	public double getNormalisedDistance(ArrayList<Double> normalisedPos) {
		double sum = 0;
		for (int i = 0; i < this.normalisedPosition.size(); i++) {
			double x1 = this.normalisedPosition.get(i);
			double x2 = normalisedPos.get(i);
			sum += Math.pow(x1 - x2, 2);
		}
		return Math.sqrt(sum);		
	}
	
	
	//## Fitnes:
	public double getFitnes() {
		return fitnes;
	}

	public double getAverageFitnes() {
		return averageFitnes;
	}

	
	private ArrayList<Double> calculateNormalisedPosition() {
		int size = originalPosition.size() - 1; //tle je treba narest se za vec dimenzij
		
		ArrayList<Double> preslikaniXi = new ArrayList<Double>(size);
		
		//IZRACUNA FAKTOR (oddaljenost izhodisca od projekcije na kocko)
		//odstrani zadnjo dimenzijo ali vec..
		for ( int i = 0; i < size; i++ ) {
			double a = originalPosition.get(i);
			preslikaniXi.add(a);		
		}

		//NAJDE RAZDALJO OD IZHODISCA?:
		//preslika tocko na kroglo	
		GParticle.correct(preslikaniXi);
		//preslika tocko na kocko
		GParticle.correctKocka(preslikaniXi);
		double razdalja = GParticle.dobiRazdaljo(preslikaniXi);
		
		for ( int i = 0; i < size; i++ ) {
			double a = originalPosition.get(i) * razdalja;
			preslikaniXi.set(i, a);
		}
		return preslikaniXi;
	}

	private double calculateFitnes() {
		double fit = prob.enacba.calculate(getDenormalisedPosition());
		return fit;		
	}
	
	private static double preslikajDimenzijo (double stevilo, double from, double to) {	
		double razpon = to - from;
		return ((stevilo + 1.0) * (razpon / 2.0) ) 
					+ from;
	}
	

	/**
	 * 				p1*r1 + p2*r2 + ...
	 * avgFitnes = ----------------------
	 *					r1 + r2 + ... 
	 */
	public double calculateAverageFitnes() {
		IntegerWrapper noOfNeighbours = new IntegerWrapper();
		averageFitnes = GParticleContainer.getFitnesOfLocation(listOfPoints, 
				this.normalisedPosition, radius, true, 50, //MAX_NEIGHBOURS
				noOfNeighbours);
		stSosedov = noOfNeighbours.get();
		return averageFitnes;
	}
	
			/*
	public double calculateAverageFitnes() {
		double deljenec = 0; 
		double delitelj = 0;
		int stevec = 0;
		
		for (Point p : listOfPoints) {
			double distance = getNormalisedDistance(p);
			if (distance < radius) {
				double k = (radius-distance)/radius;
				deljenec += p.fitnes * k;
				delitelj += k;
				stevec++;
			}
		}
		stSosedov = stevec;
		averageFitnes = deljenec / delitelj;
		return averageFitnes;
	}
	*/
			
	public int getNoOfNeighbours() {
		return stSosedov;
	}

	@Override
	public String toString() {
		String out = String.format ("~~~~ID:%d~~GE:%d~~F:%.2f~~AF:%.2f~~N:%d~", 
								pointId, generationId, fitnes, averageFitnes, stSosedov);
		
		String denor = izpisiTocko(getDenormalisedPosition());
		out = out.concat("~D:"+denor);
		String nor = izpisiTocko(getNormalisedPosition());
		out = out.concat("<-N:"+nor);
		String orig = izpisiTocko(getOriginalPosition());
		out = out.concat("<-O:"+orig);
		
		out = out.concat("~~~~");
		return out;
	}
	
	private String izpisiTocko(ArrayList<Double> ddd) {
		String out = new String("[");
		boolean first = true;
		for (Double d : ddd) {
			if (first) {
				first = false;
			} else {
				out = out.concat("/");
			}
			String num = String.format("%.2f", d);
			out = out.concat(num);
		}
		out = out.concat("]");
		return out;
	}
		
}

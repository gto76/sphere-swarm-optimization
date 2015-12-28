package sso;
import java.util.ArrayList;
import sso.io.Parameters;
import sso.io.Problem;
import sso.io.Razpon;
import sso.io.Result;

/**
 * Particle used by the sphere swarm optimization class.
 * Knows how to move by itself, only thing it neads is
 * a global best position object.
 * @author Jure
 *
 */
public class Particle {
	
	//----
	public int noOfDim;
	Problem prob;
	Parameters param;
	public Result globalBest;
	public final ArrayList<Double> razponi;
	public boolean IZPIS;
	
	public ArrayList<Double> po; //old position
	public ArrayList<Double> pn; //new position
	public ArrayList<Double> oldOldPos; //old old position
	public double score;
	
	public ArrayList<Double> pb; //best position 
	public double personalBest; 
	//----
	
	
	/**
	 * Const
	 */
	public Particle(int noOfDim, Problem prob, Parameters param, Result globalBest, boolean IZPIS) {
				
		this.noOfDim = noOfDim;
		this.prob = prob;
		this.param = param;
		this.globalBest = globalBest;
		this.IZPIS = IZPIS;
		//personalBest = -Double.MAX_VALUE; //MINMAX
		razponi = new ArrayList<Double>(noOfDim-1);
		for (int i = 0; i < noOfDim-1; i++) {
			double razpon = prob.razponi.get(i).to - prob.razponi.get(i).from;
			razponi.add(razpon);
		}
		
		
		po = new ArrayList<Double>(noOfDim);
		pn = new ArrayList<Double>(noOfDim);
		oldOldPos = new ArrayList<Double>(noOfDim);
		if ( prob.MAX ) { //INININ
			score = -Double.MAX_VALUE; //MINMAX
			personalBest =  -Double.MAX_VALUE; //MINMAX
		}
		else {
			score = Double.MAX_VALUE; //MINMAX	
			personalBest =  Double.MAX_VALUE; //MINMAX
		}
		
		pb = new ArrayList<Double>(noOfDim);
		/*
		double r = 0;	
		for ( int j = 0; j < noOfDim; j++)
		{	
			double pnTemp = Math.random() - 0.5;
			pn.add(pnTemp);
			r += Math.pow( pnTemp, 2 );	
		}
		for ( int j = 0; j < noOfDim; j++)
		{
			double pnTemp = pn.get(j) / Math.sqrt(r);
			pn.set(j, pnTemp );
			po.add( pnTemp );
			oldOldPos.add(pnTemp + Math.random()*0.05);
		}
		
		correct();
		*/
		//calculateScore();
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
		
		//stara pozicija
		
		if ( oldOldPos.size() > 0 ) {
			r = 1;
			for(int i = 0; i < noOfDim; i++)  // in each dimension..
			{
				r -= oldOldPos.get(i) // calculate the resulting distance
				* po.get(i);
			}
			r = Math.sqrt(2*r) * (hitrost);	
	
			if ( r != Double.NaN && r > param.ZELO_BLIZU ) {
			
				for(int i = 0; i < noOfDim; i++) { // in each dimension..
					
					double r2 = ( oldOldPos.get(i) - po.get(i) ) / r;
					
					r2 = r2 * param.fHitrost;
					
					double position = pn.get(i);
					pn.set( i, position - r2 );
				
				}	
			}
		}
	
		//personal Best:
		
		if ( pb.size() > 0 ) {
			r = 1;
			for(int i = 0; i < noOfDim; i++)  // in each dimension..
			{
				r -= pb.get(i) // calculate the resulting distance
				* po.get(i);
			}
			r = Math.sqrt(2*r) * hitrost;	
				
			if ( r != Double.NaN && r > param.ZELO_BLIZU ) { //ININININ
				
				for(int i = 0; i < noOfDim; i++) { // in each dimension..
					
					double r2 = ( pb.get(i) - po.get(i) ) / r;
					
					r2 = r2 * param.fPersonal;
					
					double position = pn.get(i);
					pn.set( i, position + r2 );
				
				}
			}
		}
		
		
		//global Best:

		if ( globalBest.xxx.size() > 0 ) {
			r = 1;
			//System.out.println("NO of dim: "+noOfDim);
			for(int i = 0; i < noOfDim; i++)  // in each dimension..
			{
				r -= globalBest.xxx.get(i) // calculate the resulting distance
				* po.get(i);
			}
			r = Math.sqrt(2*r) * hitrost;	
	
			if ( r != Double.NaN && r > param.ZELO_BLIZU ) { //ININININ
				
				for(int i = 0; i < noOfDim; i++) { // in each dimension..
					
					double r2 = ( globalBest.xxx.get(i) - po.get(i) ) / r;
					
					r2 = r2 * param.fGlobal;
					
					double position = pn.get(i);
					pn.set( i, position + r2 );
				
				}
			}
		}	
		
		//spravi v staro pozicijo
		for(int i = 0; i < noOfDim; i++) { // in each dimension..
			double position = po.get(i);
			oldOldPos.set( i, position );
		}
		
		correct();	// Correct movements into spere with radius 1 and update them
		
	}
	
	/**
	 * Correct data within radius 1 sphere and update model.
	 */
	public void correct()
	{
		double r = 0;
		
		for( int j=0; j < pn.size() ; j++)
		{
			r += Math.pow( pn.get(j), 2 );
		}
		
		r = Math.sqrt(r);
		for( int j=0; j < pn.size() ; j++)
		{
			double pnTemp = pn.get(j);
			pnTemp /= r;
			pn.set(j, pnTemp );
			po.set(j, pnTemp );	
		}
	}
	
	/**
	 * Calculates the f(x) of a given function.
	 */
	public void calculateScore() {
					
		//preslikaj brez zadnje dimenzije
		ArrayList<Double> preslikaniXi = preslikajParticle(pn);
				
		//poslji funkciji
		score = prob.enacba.calculate(preslikaniXi);
			
		if ( isBetter(score , personalBest) ) { //MINMAX
			personalBest = score;
			pb.clear();
			for (double p : pn) {
				   pb.add(p);
			}
			if ( isBetter(score, globalBest.fx) ) { //MINMAX
				globalBest.fx = score;
				globalBest.xxx.clear();
				for (double p : pn) {
					globalBest.xxx.add(p);
				}
				globalBest.xxxPreslikani.clear();
				globalBest.xxxPreslikani = preslikajParticle(pn);
				if ( IZPIS ) {
					System.out.print("Novi najboljsi rezultat: "+globalBest.fx);
					System.out.print(" Resitev: "+ globalBest.xxx.toString() + " | ");
					/*
					for ( double a : pn ) {
						double b = preslikaj(a);
						System.out.print(" "+b+" "); 	
					}
					*/
					System.out.print(preslikajParticle(pn).toString());
					System.out.println();
				}
			}
		}
					
	}
	
	/**
	 * preslika cel particle iz osnovnih koordinat (-1,1) v razpon,
	 * ki ga je podal uporabnik. (in odstrani zadnjo dimenzijo)
	 */
	public ArrayList<Double> preslikajParticle (ArrayList<Double> par) {
		int size = prob.steviloSpremenljivk;
		ArrayList<Double> preslikaniXi = new ArrayList<Double>(size);
		
		//IZRACUNA FAKTOR (oddaljenost izhodisca od projekcije na kocko)
		
		//odstrani zadnjo dimenzijo
		for ( int i = 0; i < size; i++ ) {
			double a = par.get(i);
			preslikaniXi.add(a);		
		}
		
		//preslika tocko na kroglo	
		correct(preslikaniXi);
		
		//preslika tocko na kocko
		correctKocka(preslikaniXi);
		
		double razdalja = dobiRazdaljo(preslikaniXi);
		
		for ( int i = 0; i < size; i++ ) {
			double a = par.get(i) * razdalja;
			double x = preslikajDimenzijo(a, i);
			preslikaniXi.set(i, x);
		}
		return preslikaniXi;
	}
	

	public double preslikajDimenzijo (double stevilo, int dimenzija) {	
		double min = prob.razponi.get(dimenzija).from;
		return ((stevilo + 1.0) * (razponi.get(dimenzija) / 2.0) ) 
					+ min;
	}
	
	public double dobiRazdaljo(ArrayList<Double> in) {
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
	public void correct(ArrayList<Double> in)
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
	public void correctKocka(ArrayList<Double> in)
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

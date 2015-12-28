package sso;
import java.util.ArrayList;

import sso.io.Parameters;
import sso.io.Problem;
import sso.io.Result;


public class ParticleContainer {
	
	private boolean IZPIS = false;
	
	private final int noOfDim;
	public final ArrayList<Particle> particles;
	private Result globalBest;
	
	//##
	//## Cons:
	//##
	public ParticleContainer(Problem prob, Parameters param, Result globalBest) {
		noOfDim = prob.steviloSpremenljivk + 1;
		this.globalBest = globalBest;
		
		particles = new ArrayList<Particle>(param.steviloParticlov);
		
		for ( int i = 0; i < param.steviloParticlov; i++ ) {
			Particle novi = new Particle(noOfDim, prob, param, globalBest, IZPIS);
			particles.add(novi);
		}
		initPoints();
	}
	
	//##
	//## Initialize Points:
	//##
	public void initPoints() {
		
		for ( Particle rozca : particles ) {
			rozca.pn.clear();
			rozca.po.clear();			
		}
		
		//int stAtributov = Iris.prim.get(0).attributes.size(); 
		int p = particles.size();
		
		for ( int i = 0; i < p; i++) {
			double r = 0;
			
			for ( int j = 0; j < noOfDim; j++) {
				//double pnTemp = 0.5;
				double pnTemp = (Math.random() - 0.5) * 0.01;
				particles.get(i).pn.add( pnTemp );
				//Iris.prim.get(i).po.add( 0.001 );
				r += Math.pow( pnTemp, 2 );
			}
			//Iris.prim.get(i).correct();
			
			for ( int j = 0; j < noOfDim; j++) {
				
				double pnTemp = particles.get(i).pn.get(j) / Math.sqrt(r);
				particles.get(i).pn.set(j, pnTemp );
				particles.get(i).po.add( pnTemp );
				particles.get(i).oldOldPos.add(pnTemp + Math.random()*0.05);	
			}					
		}
		
		for ( Particle primerek : particles ) {
			primerek.correct();
		}
		
	}
	
	public Result getGlobaBest() {
		return globalBest;
	}
	

}

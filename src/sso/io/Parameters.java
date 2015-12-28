package sso.io;

public class Parameters {
	
	public final double ZACETNA_HITROST = 0.1; 
	public final double KONCNA_HITROST = 300; //300
	
	public final double FAKTOR_OHLAJANJA;
	public final double ZELO_BLIZU;
	
	public final int steviloIteracij;
	
	public final int steviloParticlov;
	public final double fHitrost; 
	public final double fPersonal; 
	public final double fGlobal;
	
	public Parameters(double ohlajanje, double ZELO_BLIZU, int steviloParticlov, int steviloIteracij, 
				double fHitrost, double fPersonal, double fGlobal) {
		
		this.FAKTOR_OHLAJANJA = ohlajanje;
	
		this.ZELO_BLIZU = ZELO_BLIZU;
		
		this.steviloParticlov = steviloParticlov;
		this.steviloIteracij = steviloIteracij;
		
		this.fHitrost = fHitrost;
		this.fPersonal = fPersonal;
		this.fGlobal = fGlobal;
		
	}

	@Override
	public String toString() {		
		String string = new String(ZACETNA_HITROST + "\t" + FAKTOR_OHLAJANJA 
				 + "\t" + ZELO_BLIZU + "\t" + steviloParticlov + "\t" 
				 + steviloIteracij);
		return string;
	}
	
}

package sso.functions.meta;

import java.util.ArrayList;

import sso.Optimizer;
import sso.SphereSwarmOptimization;
import sso.io.Parameters;
import sso.io.Razpon;

public class FMetaSSO extends FMeta {
	
	public FMetaSSO() {
		super("FMetaOpenSSO", new SphereSwarmOptimization(false,false));
	}

	@Override
	Parameters getParameters(ArrayList<Double> xxx) {
		double zeloBlizu = 0.00001;
		int stParticlov = 60;
		int stIteracij = 60;

		double fHitrost = xxx.get(0);
		double fPersonal = 2; //!!!!!!!!!!!!!!!!!!!!!
		double fGlobal = xxx.get(1);
		double ohlajanje = xxx.get(2);
	
		Parameters parameters = new Parameters( ohlajanje, zeloBlizu,
				stParticlov, stIteracij, fHitrost, fPersonal, fGlobal);		
		
		return parameters;
	}

	@Override
	public ArrayList<Razpon> getDefaultRazponi() {
		ArrayList<Razpon> razponi = new ArrayList<Razpon>();
		razponi.add(new Razpon(0.2, 4)); //moment
		razponi.add(new Razpon(0.2, 4)); //fGlobal	
		razponi.add(new Razpon(1, 4)); //ohlajanje
		return razponi;
		
		/*
		razponi.add(new Razpon(0.2, 2)); //moment
		razponi.add(new Razpon(0.2, 2)); //fGlobal	
		razponi.add(new Razpon(1, 3)); //ohlajanje
		return razponi;
		
		/*
		razponi.add(new Razpon(0.9, 2.5)); //fGlobal
		razponi.add(new Razpon(1.3, 10)); //ohlajanje
		razponi.add(new Razpon(-2, 2)); //moment
		
		razponi.add(new Razpon(-10, 10)); //moment
		razponi.add(new Razpon(-10, 10)); //personal
		razponi.add(new Razpon(-10, 10)); //fGlobal	
		razponi.add(new Razpon(0.5, 5)); //ohlajanje
		
		razponi.add(new Razpon(-10, 10)); //moment
		razponi.add(new Razpon(1, 10)); //fGlobal	
		razponi.add(new Razpon(1, 6)); //ohlajanje
		*/
	}

}

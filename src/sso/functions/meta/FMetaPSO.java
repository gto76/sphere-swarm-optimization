package sso.functions.meta;

import java.util.ArrayList;

import sso.PSO;
import sso.io.Parameters;
import sso.io.Razpon;

public class FMetaPSO extends FMeta {

	public FMetaPSO() {
		super("FMetaOpenPSO", new PSO(false, false));
	}

	@Override
	Parameters getParameters(ArrayList<Double> xxx) {
		double zeloBlizu = Double.NaN;
		double ohlajanje = Double.NaN;
		
		int stParticlov = 60;
		int stIteracij = 60;

		double fHitrost = xxx.get(0);
		double fPersonal = xxx.get(1);
		double fGlobal = xxx.get(2);
		
		Parameters parameters = new Parameters( ohlajanje, zeloBlizu,
				stParticlov, stIteracij, fHitrost, fPersonal, fGlobal);	
		
		return parameters;
		
	}

	@Override
	public ArrayList<Razpon> getDefaultRazponi() {
		ArrayList<Razpon> razponi = new ArrayList<Razpon>();
		razponi.add(new Razpon(-2, 4)); //MfHitrost
		razponi.add(new Razpon(-4, 4)); //MfPersonal
		razponi.add(new Razpon(-4, 6)); //MfGlobal
		return razponi;
	}

}



























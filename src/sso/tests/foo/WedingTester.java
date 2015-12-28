package sso.tests.foo;

import java.util.ArrayList;

import _._;

import sso.SphereSwarmOptimization;
import sso.functions.poroka.FPoroka;
import sso.functions.standard.Schwefel;
import sso.io.Parameters;
import sso.io.Problem;
import sso.io.Razpon;
import sso.io.Result;

public class WedingTester {

	public static void main(String[] args) {
		
		// PARAMETRI:
		double ohlajanje = 2;
		double zeloBlizu = 0.00001;
		int stParticlov = 400;
		int stIteracij = 200;
/*
		double fHitrost = 2.12;
		double fPersonal = 1.59;
		double fGlobal = 2.95;
*/
		double fHitrost = 1.2;
		double fPersonal = 1;
		double fGlobal = 1.4;
		
		// PROBLEM:
		String fileName = "D:\\My Documents\\Java\\poroka_input.tar\\in\\in5.txt";
		
		//ArrayList<Razpon> razponi = Razpon.getRazponi(-1, 1, 5);
		FPoroka fp =new FPoroka(fileName);
		ArrayList<Razpon> razponi = fp.getRazponi();
		Problem pr = new Problem( razponi, false, fp );
		
		Parameters pa = new Parameters(ohlajanje, zeloBlizu, stParticlov,
				stIteracij, fHitrost, fPersonal, fGlobal );
		
		
		SphereSwarmOptimization sso = new SphereSwarmOptimization(false, true
				);
		Result res = sso.run(pr, pa);
		
		int[][] resitev = fp.getNajboljsaPoseditev();
		FPoroka.izpisi(resitev);
		
		fp.najdiLokalniMinNajboljseResitve();
		resitev = fp.getNajboljsaPoseditev();
		FPoroka.izpisi(resitev);
		_.p("Nova kazen: " + fp.najKazen);
		
		System.out.println(res.toString());
		System.out.println("Koncna hitrost: "+sso.getHitrost());
		
	}

}

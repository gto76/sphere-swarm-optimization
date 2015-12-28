package sso.functions.meta;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import sso.Optimizer;
import sso.SphereSwarmOptimization;
import sso.functions.*;
import sso.functions.cute.*;
import sso.functions.standard.*;
import sso.io.EnacbaWithParameters;
import sso.io.Parameters;
import sso.io.Problem;
import sso.io.Razpon;
import sso.io.Result;

/**
 * Funkcija ki pozene SphereSwarmOptimization z podanimi parametri
 * na vec testnih funkcijah in vrne vsoto njihovih rezultatov
 * @author Jure
 *
 */
public abstract class FMeta extends Function {
	
	private final int ITER = 1;
	private final Optimizer opti;
	public String getOptimizerName() {
		return opti.getName();
	}
	
	public FMeta(String name, Optimizer opti) {
		super(name);
		this.opti = opti;
	}
	
	abstract Parameters getParameters(ArrayList<Double> xxx);
	
	@Override
	public double calculate(ArrayList<Double> xxx) {
		
		Parameters parameters = getParameters(xxx);

		double sum = 0;
		for (int i = 0; i < ITER; i++) {
			sum += runTestFunctions(parameters);
		}
		sum /= ITER;
		
		return sum;		
	}
	
	private double runTestFunctions(Parameters parameters) {
		
		//##
		//## Testne Funkcije
		//##		
		
		EnacbaWithParameters ewp;
		HashMap<EnacbaWithParameters, Double> enacbaParametriDelitelj =
			new HashMap<EnacbaWithParameters, Double>();

		ewp = new EnacbaWithParameters(new Explin(), parameters);
		enacbaParametriDelitelj.put(ewp, 150000.0);
		ewp = new EnacbaWithParameters(new Qudlin(), parameters);
		enacbaParametriDelitelj.put(ewp, 150.0);
		ewp = new EnacbaWithParameters(new Sineali(), parameters);
		enacbaParametriDelitelj.put(ewp, 170.0);
		ewp = new EnacbaWithParameters(new Schwefel(), parameters);
		enacbaParametriDelitelj.put(ewp, 480.0);
		ewp = new EnacbaWithParameters(new Rastrigin(), parameters);
		enacbaParametriDelitelj.put(ewp, 7.0);
		ewp = new EnacbaWithParameters(new Ackley(), parameters);
		enacbaParametriDelitelj.put(ewp, 0.36);
		ewp = new EnacbaWithParameters(new Griewank(), parameters);
		enacbaParametriDelitelj.put(ewp, 0.23);
		ewp = new EnacbaWithParameters(new FB1(), parameters);
		enacbaParametriDelitelj.put(ewp, 4.3);
		ewp = new EnacbaWithParameters(new FB2(), parameters);
		enacbaParametriDelitelj.put(ewp, 17.5);
		ewp = new EnacbaWithParameters(new FB3(), parameters);
		enacbaParametriDelitelj.put(ewp, 2.5);
		
		return calculateAverage(enacbaParametriDelitelj);
		
	}
	
	private double calculateAverage(
			HashMap<EnacbaWithParameters, Double> epd) {
		double sum = 0;
		for (Entry<EnacbaWithParameters, Double>  en : epd.entrySet()) {
			double res = getDiff(en.getKey().enacba, en.getKey().paremeters);
			sum += res / en.getValue();
		}
		return sum / epd.size();		
	}
	
	private double getDiff(Function fun, Parameters par) {
		return testFunction(fun, par)- fun.getBestPossibleResult();
	}
	
	private double testFunction(Function fun, Parameters par) {
		Problem problem = new Problem ( fun.getDefaultRazponi(), false, fun );
		return getResult(problem, par);
	}
	
	private double getResult(Problem problem, Parameters parameters) {	
		// TODO: here could be problems if not everything is reset before
		//new run()
		//SphereSwarmOptimization sso = new SphereSwarmOptimization(false, false);
		//Result res = sso.run(problem, parameters);
		Result res = opti.run(problem, parameters);
		return res.fx;	
	}

	@Override
	public double getBestPossibleResult() {
		return 0;
	}

}

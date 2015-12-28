package sso;

import sso.io.Parameters;
import sso.io.Problem;
import sso.io.Result;

public abstract class Optimizer {
	
	final boolean ANIMATE;
	final int REFRESH_RATE = 1000; //za animacijo
	final boolean POTRDILO = false;
	final boolean PRINT;
	
	public Optimizer(boolean print, boolean animate) {
		this.PRINT = print;
		this.ANIMATE = animate;
	}

	public abstract String getName();
	public abstract Result run(Problem problem, Parameters parameters);
		
}

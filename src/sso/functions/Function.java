package sso.functions;

import java.util.ArrayList;

import sso.io.Razpon;

public abstract class Function {

	private String name = null;
	
	public Function( String name) {
		this.name = name;
	}
	
	abstract public double calculate(ArrayList<Double> x);		
	abstract public ArrayList<Razpon> getDefaultRazponi();
	abstract public double getBestPossibleResult();

	@Override
	public String toString() {
		return name;
	}
	
}

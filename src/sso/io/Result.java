package sso.io;
import java.util.ArrayList;


public class Result {

	public double fx;
	public ArrayList<Double> xxx;
	public ArrayList<Double> xxxPreslikani;
	
	public Result( double fx, ArrayList<Double> xxx, 
						ArrayList<Double> xxxPreslikani  ) {
		this.fx = fx;
		this.xxx = xxx;
		this.xxxPreslikani = xxxPreslikani;
	}

	@Override
	public String toString() {
		return fx + "\t" + xxxPreslikani.toString();
	}
	
	
}

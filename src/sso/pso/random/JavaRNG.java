// ------------------------------------------------------
// SwarmOps - Numeric and heuristic optimization for Java
// Copyright (C) 2003-2011 Magnus Erik Hvass Pedersen.
// Please see the file license.txt for license details.
// SwarmOps on the internet: http://www.Hvass-Labs.org/
// ------------------------------------------------------

package sso.pso.random;

/**
 * Wrapper for a java.util.Random object.
 */
public class JavaRNG extends Random {
	/**
	 * Construct the object and wrap a new instance of java.util.Random.
	 */
	public JavaRNG() {
		rng = new java.util.Random();
	}

	/**
	 * Construct the object and wrap the supplied rng.
	 */
	public JavaRNG(java.util.Random rng) {
		this.rng = rng;
	}

	/** Wrapped RNG used for generating random numbers. */
	java.util.Random rng;

	@Override
	public String getName() {
		return "JavaRNG";
	}

	@Override
	public double nextUniform() {
		return rng.nextDouble();
	}

	@Override
	public int nextIndex(int n) {
		return rng.nextInt(n);
	}

	@Override
	public boolean nextBoolean() {
		return rng.nextBoolean();
	}
}

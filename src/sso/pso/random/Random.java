// ------------------------------------------------------
// SwarmOps - Numeric and heuristic optimization for Java
// Copyright (C) 2003-2011 Magnus Erik Hvass Pedersen.
// Please see the file license.txt for license details.
// SwarmOps on the internet: http://www.Hvass-Labs.org/
// ------------------------------------------------------

package sso.pso.random;

/**
 * Base-class for a Random Number Generator (RNG) with the methods required by
 * SwarmOps. It is rumored that earlier Java versions had faulty implementations
 * of java.util.Random which is the reason some of the methods (e.g.
 * nextGaussian() and nextInt(n)) have been re-implemented here, to ensure
 * correctness regardless of what Java version is being used.
 */
public abstract class Random {
	/** 
	 * Construct the object.
	 */
	public Random() {
		super();
	}

	/**
	 * Name of the RNG.
	 */
	public abstract String getName();

	/**
	 * Draw a uniform random number in the exclusive range (0,1). It is
	 * important that it does not return the end-point value one which would
	 * cause incorrect output of nextIndex(n).
	 */
	public abstract double nextUniform();

	/**
	 * Draw a uniform random number in the exclusive range (lower, upper).
	 */
	public double nextUniform(double lower, double upper) {
		return nextUniform() * (upper - lower) + lower;
	}

	/**
	 * Draw a random integer from the set {0, .., n-1} with uniform probability.
	 */
	public int nextIndex(int n) {
		// The default implementation uses nextUniform() to generate an integer.
		// This is because many PRNGs must use division instead of modulo
		// arithmetics due to the lower-order bits having little randomness.
		// Assume nextUniform() cannot generate an exact value of one,
		// otherwise this must have been taken into account to ensure
		// uniform probability of choosing the different indices.

		assert n >= 1;

		double indexD = nextUniform(0, n);

		assert indexD > 0 && indexD < n;

		// Truncate.
		int index = (int) indexD;

		assert index >= 0 && index <= n - 1;

		return index;
	}

	/**
	 * Draw two distinct integers from the set {0, .., n-1} with equal
	 * probability.
	 */
	public int[] nextIndex2(int n) {
		assert n >= 2;

		int r1, r2;

		r1 = nextIndex(n);
		r2 = nextIndex(n - 1);

		int i1 = r1;
		int i2 = (r1 + r2 + 1) % n;

		assert i1 != i2;

		return new int[] { i1, i2 };
	}

	/**
	 * Draw a random boolean with equal probability of true and false.
	 */
	public boolean nextBoolean() {
		return nextBoolean(0.5);
	}

	/**
	 * Draw a random boolean with probability p of true and (1-p) of false.
	 */
	public boolean nextBoolean(double p) {
		return nextUniform() < p;
	}

	/**
	 * Next Gaussian random number.
	 */
	private double gaussian;

	/**
	 * Does gaussian hold a value?
	 */
	private boolean gaussianReady = false;

	/**
	 * Draw a Gaussian (or normally) distributed random number, with designated
	 * mean and deviation.
	 * 
	 * @param mean
	 *            mean of the normal distribution, e.g. 0.
	 * @param deviation
	 *            deviation of the normal distribution, e.g. 1.
	 * @return
	 */
	public double nextGaussian(double mean, double deviation) {
		return deviation * nextGaussian() + mean;
	}

	/**
	 * Draw a Gaussian (or normally) distributed random number, with mean 0 and
	 * deviation 1.
	 */
	public double nextGaussian() {
		double value;

		if (gaussianReady) {
			value = gaussian;
			gaussianReady = false;
		} else {
			// Pick two uniform numbers in the unit-radius 2-dim ball.
			Disk disk = new Disk(this);

			// Use notation from Numeric Recipes book
			double v1 = disk.x;
			double v2 = disk.y;
			double rsq = disk.sumSquares;

			double fac = Math.sqrt(-2.0 * Math.log(rsq) / rsq);

			// Now make the Box-Muller transformation to get two normal
			// deviates.
			// Return one and save the other for next time.
			gaussian = v1 * fac;
			gaussianReady = true;

			value = v2 * fac;
		}

		return value;
	}
}

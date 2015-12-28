// ------------------------------------------------------
// SwarmOps - Numeric and heuristic optimization for Java
// Copyright (C) 2003-2011 Magnus Erik Hvass Pedersen.
// Please see the file license.txt for license details.
// SwarmOps on the internet: http://www.Hvass-Labs.org/
// ------------------------------------------------------

package sso.pso.random;

/**
 * Generate a uniform random point from the unit-radius 2-dimensional disk.
 */
public class Disk {
	/**
	 * Create the object and draw a uniform random point on the 2-dimensional disk.
	 */
	public Disk(Random random) {
		// Pick two uniform numbers in the square (-1,1) x (-1,1). See if the
		// numbers are inside the unit circle, and if they are not, try again.

		// Successful points occupy the inside of the unit circle, whose area is
		// pi, or about 3.14. Since we sample uniformly from a square of size 4,
		// the probability of the loop succeeding in a single iteration is pi/4,
		// or about 0.7854.
		// Probability of success in two iterations is therefore 0.954, in three
		// iterations it is about 0.990, and the probability of success in
		// four successive iterations is approximately 0.998, etc. -- provided
		// there
		// is no correlation between calls to Uniform(-1, 1).

		// The average number of iterations before success is 1.27

		double x, y, sumSquares;

		do {
			x = random.nextUniform(-1, 1);
			y = random.nextUniform(-1, 1);
			sumSquares = x * x + y * y;
		} while (sumSquares > 1);

		// Assign local variables to class-fields.
		this.x = x;
		this.y = y;
		this.sumSquares = sumSquares;
	}

	/**
	 * Random point x.
	 */
	public final double x;

	/**
	 * Random point y.
	 */
	public final double y;

	/**
	 * Equals x*x + y*y
	 */
	public final double sumSquares;
}

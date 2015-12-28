// ------------------------------------------------------
// SwarmOps - Numeric and heuristic optimization for Java
// Copyright (C) 2003-2011 Magnus Erik Hvass Pedersen.
// Please see the file license.txt for license details.
// SwarmOps on the internet: http://www.Hvass-Labs.org/
// ------------------------------------------------------

package sso.pso;

import java.util.Locale;

import sso.Globals;

/**
 * Collection of commonly used methods.
 */
public class Tools {

	/**
	 * Return whether feasibility (constraint satisfaction) of new candidate
	 * solution is same as or better than feasibility of old candidate solution.
	 * 
	 * @param oldFeasible
	 *            feasibility of old candidate solution.
	 * @param newFeasible
	 *            feasibility of new candidate solution.
	 */
	public static boolean isBetterFeasible(boolean oldFeasible,
			boolean newFeasible) {
		return (!oldFeasible || newFeasible);
	}

	/**
	 * Return whether the new candidate solution is better than the old
	 * depending on their feasibility (constraint satisfaction) and fitness.
	 * 
	 * @param oldFeasible
	 *            feasibility of old candidate solution.
	 * @param newFeasible
	 *            feasibility of new candidate solution.
	 * @param oldFitness
	 *            fitness of old candidate solution.
	 * @param newFitness
	 *            fitness of new candidate solution.
	 */
	public static boolean isBetterFeasibleFitness(boolean oldFeasible,
			boolean newFeasible, double oldFitness, double newFitness) {
		return ((!oldFeasible && (newFeasible || newFitness < oldFitness)) || (oldFeasible
				&& newFeasible && newFitness < oldFitness));
	}

	/**
	 * Bound a value between lower and upper boundaries.
	 * 
	 * @param x
	 *            value to be bounded.
	 * @param lower
	 *            lower boundary.
	 * @param upper
	 *            upper boundary.
	 * @return bounded value.
	 */
	public static double bound(double x, double lower, double upper) {
		assert upper >= lower;

		double y;

		if (x < lower) {
			y = lower;
		} else if (x > upper) {
			y = upper;
		} else {
			y = x;
		}

		return y;
	}

	/**
	 * Bound array of values between lower and upper boundaries.
	 * 
	 * @param x
	 *            array of values to be bounded (in-place).
	 * @param lower
	 *            lower boundaries.
	 * @param upper
	 *            upper boundaries.
	 */
	public static void bound(double[] x, double[] lower,
			double[] upper) {
		assert x.length == lower.length && x.length == upper.length;

		for (int i = 0; i < x.length; i++) {
			x[i] = bound(x[i], lower[i], upper[i]);
		}
	}

	/**
	 * Return whether array of values is between lower and upper boundaries.
	 * 
	 * @param x
	 *            array of values.
	 * @param lower
	 *            lower boundaries.
	 * @param upper
	 *            upper boundaries.
	 */
	public static boolean isBetweenBounds(double[] x, double[] lower,
			double[] upper) {
		assert x.length == lower.length && x.length == upper.length;

		boolean retVal = true;

		for (int i = 0; retVal && i < x.length; i++) {
			retVal = retVal && x[i] >= lower[i] && x[i] <= upper[i];
		}

		return retVal;
	}

	/**
	 * Initialize array with value.
	 * 
	 * @param x
	 *            array to be initialized.
	 * @param value
	 *            initialize array with this value.
	 */
	public static void initialize(double[] x, double value) {
		for (int i = 0; i < x.length; i++) {
			x[i] = value;
		}
	}

	/**
	 * Initialize array with uniform random values between given boundaries.
	 * 
	 * @param x
	 *            array to be initialized.
	 * @param lower
	 *            lower boundary for random value.
	 * @param upper
	 *            upper boundary for random value.
	 */
	public static void initializeUniform(double[] x, double lower, double upper) {
		for (int i = 0; i < x.length; i++) {
			x[i] = Globals.random.nextUniform(lower, upper);
		}
	}

	/**
	 * Initialize array with uniform random values between given boundaries.
	 * 
	 * @param x
	 *            array to be initialized.
	 * @param lower
	 *            array of lower boundaries for random values.
	 * @param upper
	 *            array of upper boundaries for random values.
	 */
	public static void initializeUniform(double[] x, double[] lower,
			double[] upper) {
		assert x.length == lower.length && x.length == upper.length;

		for (int i = 0; i < x.length; i++) {
			x[i] = Globals.random.nextUniform(lower[i], upper[i]);
		}
	}

	/**
	 * Initialize array with the range between the boundaries. That is, x[i] =
	 * upper[i] - lower[i].
	 * 
	 * @param x
	 *            array to be initialized.
	 * @param lower
	 *            array of lower boundary.
	 * @param upper
	 *            array of upper boundary.
	 */
	public static void initializeRange(double[] x, double[] lower,
			double[] upper) {
		assert x.length == lower.length && x.length == upper.length;

		for (int i = 0; i < x.length; i++) {
			x[i] = upper[i] - lower[i];

			assert x[i] >= 0;
		}
	}

	/**
	 * Copy array without allocating new destination array.
	 * 
	 * @param src
	 *            source-array.
	 * @param dest
	 *            destination-array.
	 */
	public static void copy(double[] src, double[] dest) {
		assert dest.length == src.length;

		System.arraycopy(src, 0, dest, 0, dest.length);
	}

	/**
	 * Pick a uniform random sample from [x-range, x+range].
	 */
	public static double sample(double x, double range) {
		assert range >= 0;

		// Pick a sample from within the bounded range.
		double y = Globals.random.nextUniform(x - range, x + range);

		return y;
	}

	/**
	 * First bound the sampling range [x-range, x+range] using lowerBound and
	 * upperBound respectively, and then pick a uniform random sample from the
	 * bounded range. This avoids samples being boundary points.
	 */
	public static double sampleBounded(double x, double range,
			double lowerBound, double upperBound) {
		assert lowerBound < upperBound;
		assert range >= 0;

		double l, u; // Sampling range.
		double y; // Actual sample.

		// Adjust sampling range so it does not exceed bounds.
		l = Math.max(x - range, lowerBound);
		u = Math.min(x + range, upperBound);

		// Pick a sample from within the bounded range.
		y = Globals.random.nextUniform(l, u);

		return y;
	}

	/**
	 * Euclidian norm (or length) of a numeric vector.
	 */
	public static double norm(double[] x) {
		double sum = 0;

		for (int i = 0; i < x.length; i++) {
			double elm = x[i];
			sum += elm * elm;
		}

		return Math.sqrt(sum);
	}

	public static Locale locale = Locale.US;

	/**
	 * Convert numeric value d to a string with convenient formatting.
	 */
	public static String formatNumber(Double d) {
		String s;

		if (d != null) {
			double dAbs = Math.abs(d);

			if (dAbs == 0) {
				s = "0";
			} else if (dAbs < 1e-2 || dAbs > 1e+6) {
				s = String.format(locale, "%.2e", d);
			} else if (dAbs > 1e+3) {
				s = String.format(locale, "%.0f", d);
			} else {
				s = String.format(locale, "%.2f", d);
			}
		} else {
			s = "--";
		}

		return s;
	}

	/**
	 * Convert numeric value d to a percentage string, e.g. d==0.253212 returns
	 * string "25.32%"
	 */
	public static String formatPercent(Double d) {
		return String.format(locale, "%.2f%%", d * 100);
	}

	/**
	 * Print a newline.
	 */
	public static void printNewline() {
		System.out.printf("\n");
	}

	/**
	 * Print parameters using names associated with an optimization problem.
	 * 
	 * @param problem
	 *            optimization problem with associated parameter-names.
	 * @param parameters
	 *            parameters to be printed.
	 */
	/*
	public static void printParameters(Problem problem, double[] parameters) {
		int numParameters = problem.getDimensionality();

		if (numParameters > 0) {
			assert parameters.length == numParameters;

			for (int i = 0; i < numParameters; i++) {
				String parameterName = problem.getParameterName()[i];
				double p = parameters[i];
				String pStr = String.format(locale, "%.4f", p);

				System.out.printf("\t%s = %s\n", parameterName, pStr);
			}
		} else {
			System.out.printf("\tN/A\n");
		}
	}
*/
	/**
	 * Print parameters, fitness and feasibility to Console, and print a marking
	 * if fitness was an improvement to fitnessLimit.
	 */
	public static void printSolution(double[] parameters, double fitness,
			double fitnessLimit, boolean oldFeasible, boolean newFeasible,
			boolean formatAsArray) {
		// Convert parameters to a string.
		String parametersStr = (formatAsArray) ? (arrayToString(parameters))
				: (arrayToStringRaw(parameters));

		System.out.printf("%s \t%s \t%d %s\n", parametersStr, Tools
				.formatNumber(fitness), (newFeasible) ? (1) : (0), Tools
				.isBetterFeasibleFitness(oldFeasible, newFeasible,
						fitnessLimit, fitness) ? ("***") : (""));

		// Flush stdout, this is useful if piping the output and you wish
		// to study the the output before the entire optimization run is
		// complete.
		System.out.flush();
	}

	/**
	 * Convert an array of System.double values to a string.
	 */
	public static String arrayToString(double[] x) {
		String s = "{ ";

		if (x.length > 0) {
			s += numberToString(x[0]);

			for (int i = 1; i < x.length; i++) {
				s += ", " + numberToString(x[i]);
			}
		}

		s += " }";

		return s;
	}

	/**
	 * Convert an array of System.double values to a string. Not formatted like
	 * a Java array.
	 */
	public static String arrayToStringRaw(double[] x) {
		String s = "";

		for (int i = 0; i < x.length; i++) {
			s += numberToString(x[i]) + " ";
		}

		return s;
	}

	/**
	 * Convert a double-value to a string, formatted for array output.
	 */
	public static String numberToString(double x) {
		return String.format(locale, "%.4f", x);
	}
}

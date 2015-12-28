// ------------------------------------------------------
// SwarmOps - Numeric and heuristic optimization for Java
// Copyright (C) 2003-2011 Magnus Erik Hvass Pedersen.
// Please see the file license.txt for license details.
// SwarmOps on the internet: http://www.Hvass-Labs.org/
// ------------------------------------------------------

package sso.pso.random;

/**
 * Pseudo-Random Number Generator (PRNG) using the Mersenne Twister algorithm by
 * Makoto Matsumoto and Takuji Nishimura. This implementation is rewritten from
 * their C source-code originally dated 2002/1/26. This PRNG has a very long
 * period of 2^19937-1 (approximately 4.3 x 10^6001), and is hence known as
 * MT19937. This implementation is the 32-bit version.
 * 
 * The original C source-code contains the following copyright notice which
 * still holds for this more or less direct translation to the Java language:
 * 
 * Copyright (C) 1997 - 2002, Makoto Matsumoto and Takuji Nishimura, All rights
 * reserved. /// Redistribution and use in source and binary forms, with or
 * without modification, are permitted provided that the following conditions
 * are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * 
 * 3. The names of its contributors may not be used to endorse or promote
 * products derived from this software without specific prior written
 * permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * 
 * Any feedback is very welcome.
 * http://www.math.sci.hiroshima-u.ac.jp/~m-mat/MT/emt.html email: m-mat @
 * math.sci.hiroshima-u.ac.jp (remove space)
 */
public class MersenneTwister extends Random {
	/**
	 * Construct the object. Seed the PRNG with the system-time.
	 */
	public MersenneTwister() {
		this(System.currentTimeMillis());
	}

	/**
	 * Construct the object.
	 * 
	 * @param seed
	 *            seed for the PRNG.
	 */
	public MersenneTwister(long seed) {
		super();
		setSeed(seed);
	}

	/** Array-length. */
	static final int N = 624;
	static final int M = 397;

	/** Constant vector a. */
	static final int MATRIX_A = 0x9908b0df;

	/** Most significant w-r bits. */
	static final int UPPER_MASK = 0x80000000;

	/** Least significant r bits. */
	static final int LOWER_MASK = 0x7fffffff;
	static final int[] mag01 = { 0x0, MATRIX_A };

	/** The array for the state vector. */
	int[] mt = new int[N];

	/** Index into mt-array. */
	int mti;

	/** Is PRNG ready? */
	private boolean isReady = false;

	/** Set seed. */
	private void setSeed(long seedL) {
		int seed = (int) (seedL % Integer.MAX_VALUE);

		mt[0] = seed;

		for (mti = 1; mti < N; mti++) {
			int lcg = 1812433253;
			mt[mti] = (lcg * (mt[mti - 1] ^ (mt[mti - 1] >>> 30)) + mti);
		}

		isReady = true;
	}

	/** Draw up to 32 random bits, approximately uniformly distributed. */
	public int next(int bits) {
		assert isReady;

		int y;

		if (mti >= N) {
			// Generate N words.

			int kk;

			for (kk = 0; kk < N - M; kk++) {
				y = (mt[kk] & UPPER_MASK) | (mt[kk + 1] & LOWER_MASK);
				mt[kk] = mt[kk + M] ^ (y >>> 1) ^ mag01[y & 0x1];
			}

			for (; kk < N - 1; kk++) {
				y = (mt[kk] & UPPER_MASK) | (mt[kk + 1] & LOWER_MASK);
				mt[kk] = mt[kk + M - N] ^ (y >>> 1) ^ mag01[y & 0x1];
			}

			y = (mt[N - 1] & UPPER_MASK) | (mt[0] & LOWER_MASK);
			mt[N - 1] = mt[M - 1] ^ (y >>> 1) ^ mag01[y & 0x1];

			mti = 0;
		}

		y = mt[mti++];

		/* Tempering */
		y ^= (y >>> 11);
		y ^= (y << 7) & 0x9d2c5680;
		y ^= (y << 15) & 0xefc60000;
		y ^= (y >>> 18);

		return y >>> 32 - bits;
	}

	@Override
	public String getName() {
		return "MersenneTwister19937";
	}

	@Override
	public boolean nextBoolean() {
		return next(1) != 0;
	}

	@Override
	public double nextUniform() {
		// This is the same as in new Java versions, re-written here in case an
		// old Java version is used.

		double d = (((long) next(26) << 27) + next(27)) / (double) (1L << 53);

		assert d >= 0 && d < 1.0;

		return d;
	}
}

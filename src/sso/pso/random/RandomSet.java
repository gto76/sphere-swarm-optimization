// ------------------------------------------------------
// SwarmOps - Numeric and heuristic optimization for Java
// Copyright (C) 2003-2011 Magnus Erik Hvass Pedersen.
// Please see the file license.txt for license details.
// SwarmOps on the internet: http://www.Hvass-Labs.org/
// ------------------------------------------------------

package sso.pso.random;

/**
 * A set of integers enumerated from zero and upwards that can be drawn at
 * random. Creation or resetting takes time O(n) where n is the size of the set,
 * and drawing a random element from the set takes constant time O(1). Not
 * thread-safe.
 */
public class RandomSet {
	/**
	 * Creates but does not initialize the random set.
	 * 
	 * @param rng
	 *            rng to use for random drawing.
	 * @param capacity
	 *            number of elements in the set.
	 */
	public RandomSet(Random rng, int capacity) {
		this.rng = rng;
		this.capacity = capacity;
		this.size = 0;
		elms = new int[capacity];
	}

	/**
	 * Number of elements available for drawing.
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Maximum number of elements in the set.
	 */
	public int getCapacity() {
		return capacity;
	}

	private int size;
	private int capacity;

	/**
	 * RNG used for drawing random numbers.
	 */
	Random rng;

	/**
	 * Array used for holding the elements of the set.
	 */
	private int[] elms;

	/**
	 * Swap the i'th and j'th elements of the internal array.
	 */
	private void swap(int i, int j) {
		assert i >= 0 && i < capacity;
		assert j >= 0 && j < capacity;

		int temp = elms[i];
		elms[i] = elms[j];
		elms[j] = temp;
	}

	/**
	 * Remove an element from the set.
	 * 
	 * @param index
	 *            index of element to be removed.
	 */
	void remove(int index) {
		// Various assumptions.
		assert size >= 1;
		assert index >= 0 && index < size;

		// Decrease the number of elements in the set.
		size--;

		// Swap element to be removed with the back of the array.
		swap(index, size);
	}

	/**
	 * Draw and remove an element from the set.
	 */
	public int draw() {
		// Local variables to be used.
		int index;
		int retVal;

		// Various assumptions.
		assert size > 0;

		// Get random index from remainder of set.
		index = rng.nextIndex(size);

		// Retrieve the element at that position.
		retVal = elms[index];

		// Remove that element from the set.
		remove(index);

		return retVal;
	}

	/**
	 * Reset by filling the set with integer elements enumerated from zero and
	 * upwards.
	 */
	public void reset() {
		// Reset index-variable.
		size = capacity;

		// Initialize array.
		for (int i = 0; i < size; i++) {
			elms[i] = i;
		}
	}

	/**
	 * Same as Reset() only that it is being followed by a removal of the
	 * designated element.
	 * 
	 * @param index
	 *            element to be removed after resetting.
	 */
	public void resetExclude(int index) {
		assert index >= 0 && index < capacity;

		reset();
		remove(index);
	}
}

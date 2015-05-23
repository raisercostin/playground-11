package org.raisercostin.test.cache.strategy;

import java.util.Random;

/**
 * Random Replacement (RR) Randomly selects a candidate item and discards it to
 * make space when necessary. This algorithm does not require keeping any
 * information about the access history. For its simplicity, it has been used in
 * ARM processors.[5] It admits efficient stochastic simulation.[6]
 *
 * For O(n) the strategy could check if the key is already in cache.
 *
 * @author costin
 * @param <Key>
 */
public class RandomCacheStrategy<Key> implements CacheStrategy<Key> {
    private Key[] keys;
    private Random random;

    @SuppressWarnings("unchecked")
    public RandomCacheStrategy(int maxKeys, Random random) {
	this.keys = (Key[]) new Object[maxKeys];
	this.random = random;
    }

    @Override
    public Key update(Key key) {
	boolean hit = contains(keys, key);
	if (hit) {
	    return key;
	} else {
	    int index = random.nextInt(keys.length);
	    Key result = keys[index];
	    keys[index] = key;
	    return result;
	}
    }

    private static <T> boolean contains(final T[] array, final T v) {
	for (final T e : array)
	    if (e == v || v != null && v.equals(e))
		return true;
	return false;
    }
}

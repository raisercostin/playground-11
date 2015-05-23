package org.raisercostin.test.cache.replacement;

import java.util.Arrays;

/**
 * Least Recently Used (LRU) Discards the least recently used items first. This
 * algorithm requires keeping track of what was used when, which is expensive if
 * one wants to make sure the algorithm always discards the least recently used
 * item. General implementations of this technique require keeping "age bits"
 * for cache-lines and track the "Least Recently Used" cache-line based on
 * age-bits. In such an implementation, every time a cache-line is used, the age
 * of all other cache-lines changes. LRU is actually a family of caching
 * algorithms with members including 2Q by Theodore Johnson and Dennis Shasha,
 * and LRU/K by Pat O'Neil, Betty O'Neil and Gerhard Weikum.
 *
 * @author costin
 * @param <Key>
 */
public class LeastRecentlyUsedCacheStrategy<Key> implements CacheStrategy<Key> {
    private int all = 0;
    private int hits = 0;
    private Key[] keys;
    private int current = 0;

    @SuppressWarnings("unchecked")
    public LeastRecentlyUsedCacheStrategy(int maxEntries) {
	this.keys = (Key[]) new Object[maxEntries];
    }

    @Override
    public Key update(Key key) {
	boolean hit = contains(keys, key);
	Key result = key;
	if (hit) {
	    hits++;
	} else {
	    result = keys[current];
	    keys[current] = key;
	    current = (current + 1) % keys.length;
	}
	all++;
	return result;
    }

    public static <T> boolean contains(final T[] array, final T v) {
	for (final T e : array)
	    if (e == v || v != null && v.equals(e))
		return true;
	return false;
    }

    public int countHits() {
	return 0;
    }

    public int countAll() {
	return all;
    }
    public String state(){
	return Arrays.toString(keys);
    }
}

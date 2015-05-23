package org.raisercostin.test.cache.replacement;

import java.util.*;

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
    private int maxEntries;
    private Queue<Key> keys;
    private int counter = 0;
    //statistics
    private int all = 0;
    private int hits = 0;

    @SuppressWarnings("unchecked")
    public LeastRecentlyUsedCacheStrategy(int maxEntries) {
	this.keys = new LinkedList<Key>();
	this.maxEntries = maxEntries;
    }

    @Override
    public Key update(Key key) {
	// O(n)
	boolean hit = keys.contains(key);
	Key result = key;
	if (hit) {
	    hits++;
	    keys.remove(key);
	    result = key;
	} else {
	    if (counter >= maxEntries) {
		result = keys.poll();
		counter--;
	    }else{
		result = null;
	    }
	}
	keys.add(key);
	counter++;
	all++;
	return result;
    }

    public int countHits() {
	return hits;
    }

    public int countAll() {
	return all;
    }

    public String state() {
	return keys.toString();
    }
}

package org.raisercostin.test.cache.strategy;

import java.util.*;

import org.raisercostin.test.cache.storage.StorageStrategy;

/**
 * Least Recently Used (LRU) Discards the least recently used items first. This algorithm requires keeping track of what
 * was used when, which is expensive if one wants to make sure the algorithm always discards the least recently used
 * item. General implementations of this technique require keeping "age bits" for cache-lines and track the
 * "Least Recently Used" cache-line based on age-bits. In such an implementation, every time a cache-line is used, the
 * age of all other cache-lines changes. LRU is actually a family of caching algorithms with members including 2Q by
 * Theodore Johnson and Dennis Shasha, and LRU/K by Pat O'Neil, Betty O'Neil and Gerhard Weikum.
 * 
 * @author costin
 * @param <Key>
 */
public class LeastRecentlyUsedCacheStrategy<Key> implements CacheStrategy<Key> {
	private int maxEntries;
	private Queue<Key> keys;
	private int counter = 0;
	// statistics
	private int all = 0;
	private int hits = 0;

	@SuppressWarnings("unchecked")
	public LeastRecentlyUsedCacheStrategy(int maxEntries) {
		this.keys = new LinkedList<Key>();
		this.maxEntries = maxEntries;
	}

	@Override
	public <T> void initialize(StorageStrategy<Key, T> storage) {
		// TODO improve the initialization without clearing the storage
		storage.clear();
	}

	@Override
	public Key update(Key key) {
		// O(n) - might be externalized to StorageStrategy
		boolean hit = keys.contains(key);
		Key result = key;
		if (hit) {
			hits++;
			// O(n) - removing a key in the middle
			keys.remove(key);
			counter--;
			result = key;
		} else {
			if (counter >= maxEntries) {
				result = keys.poll();
				counter--;
			} else {
				result = null;
			}
		}
		keys.add(key);
		counter++;
		all++;
		return result;
	}

	int countHits() {
		return hits;
	}

	int countAll() {
		return all;
	}

	String state() {
		return keys.toString();
	}
}

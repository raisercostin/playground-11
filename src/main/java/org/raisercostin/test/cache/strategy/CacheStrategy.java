package org.raisercostin.test.cache.strategy;

import org.raisercostin.test.cache.storage.StorageStrategy;

/**
 * A cache replacement strategy.
 * 
 * From wikipedia: In computing, cache algorithms (also frequently called replacement algorithms or replacement
 * policies) are optimizing instructions – or algorithms – that a computer program or a hardware-maintained structure
 * can follow, in order to manage a cache of information stored on the computer. When the cache is full, the algorithm
 * must choose which items to discard to make room for the new ones.
 * 
 * @see http://dhruvbird.com/lfu.pdf - LFU in O(1)
 * 
 * @see http://en.wikipedia.org/wiki/Cache_algorithms
 * @author costin
 * 
 */
public interface CacheStrategy<Key> {
	/**
	 * Allows the strategy to read the storage state on reinitialization. Needed mostly for DiskStorage.
	 */
	<T> void initialize(StorageStrategy<Key, T> storage);

	/**
	 * Notifies the strategy that a new key was needed and returns the old key that occupied the slot (null, same key,
	 * otherDifferentKey).
	 * 
	 * @param key
	 * @return key if the update is a hit
	 * @return null if the update is a miss and no key replacement is necessary
	 * @return otherDifferentKey if the update is a miss returns the otherKey that needs to be replaced by key
	 */
	Key update(Key key);
}

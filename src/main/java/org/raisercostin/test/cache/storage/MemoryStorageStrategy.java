package org.raisercostin.test.cache.storage;

import java.util.HashMap;
import java.util.Map;

/**
 * Storage is made in memory using a hashMap.
 * 
 * It is not thread safe since the safety should be assured by the client of
 * this class.
 */
public class MemoryStorageStrategy<K, V> implements StorageStrategy<K, V> {
	private Map<K, V> map = new HashMap<K, V>();

	@Override
	public void save(K key, V value) {
		map.put(key, value);
	}

	@Override
	public V loadOr(K key, V defaultValue) {
		V value = map.get(key);
		if (value == null)
			return defaultValue;
		else
			return value;
	}

	@Override
	public void remove(K key) {
		map.remove(key);
	}

	@Override
	public boolean containsKey(K key) {
		return map.containsKey(key);
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public void clear() {
		map.clear();
	}
}

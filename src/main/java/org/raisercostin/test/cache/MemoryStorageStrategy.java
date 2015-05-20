package org.raisercostin.test.cache;

import java.util.HashMap;
import java.util.Map;

public class MemoryStorageStrategy<K, V> implements Storage<K, V> {
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
}

package org.raisercostin.test.cache;

import java.util.Map;

public class CacheTemplate<Key, Value> {
	public static <Key, Value> CacheTemplate<Key, Value> create(
			Cache<Key, Value> cache, Map<Key, Value> data) {
		return new CacheTemplate<Key, Value>(cache, data);
	}

	private Cache<Key, Value> cache;
	private Map<Key, Value> data;

	private CacheTemplate(Cache<Key, Value> cache, Map<Key, Value> data) {
		this.cache = cache;
		this.data = data;
	}

	public Value getOrCompute(Key key) {
		Value result = cache.get(key);
		if (result == null) {
			result = data.get(key);
			if (result != null)
				cache.put(key, result);
		}
		return result;
	}
}

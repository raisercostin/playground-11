package org.raisercostin.test.cache;

import java.util.Map;
import java.util.Random;

import org.raisercostin.test.cache.storage.MemoryStorageStrategy;
import org.raisercostin.test.cache.strategy.LeastRecentlyUsedCacheStrategy;
import org.raisercostin.test.cache.strategy.RandomCacheStrategy;

public class CacheTemplate<Key, Value> {
	public static <Key, Value> CacheTemplate<Key, Value> createSimpleMemoryLRU(int maxEntries) {
		return create(new SimpleCache<Key, Value>(new MemoryStorageStrategy<Key, Value>(),
				new LeastRecentlyUsedCacheStrategy<Key>(maxEntries)));
	}

	public static <Key, Value> CacheTemplate<Key, Value> createSimpleMemoryRandom(int maxEntries) {
		return create(new SimpleCache<Key, Value>(new MemoryStorageStrategy<Key, Value>(),
				new RandomCacheStrategy<Key>(maxEntries, new Random(10))));
	}

	public static <Key, Value> CacheTemplate<Key, Value> create(ObservableCache<Key, Value> cache) {
		return new CacheTemplate<Key, Value>(cache, null);
	}

	public static <Key, Value> CacheTemplate<Key, Value> create(ObservableCache<Key, Value> cache, Map<Key, Value> data) {
		return new CacheTemplate<Key, Value>(cache, data);
	}

	private ObservableCache<Key, Value> cache;
	private Map<Key, Value> data;
	// encapsulated inside lock object not to be used by external objects/clients
	private final Object locker = new Object();

	private CacheTemplate(ObservableCache<Key, Value> cache, Map<Key, Value> data) {
		this.cache = cache;
		this.data = data;
	}

	/** Returns the value from cache or gets one from supplier (as a Map now). */
	public Value getOrLoad(Key key) {
		synchronized (locker) {
			Value result = cache.get(key);
			if (result == null) {
				result = data.get(key);
				if (result != null)
					cache.put(key, result);
			}
			return result;
		}
	}

	/** Returns the value from cache or onMissValue if is not found. */
	public Value getOr(Key key, Value onMissValue) {
		synchronized (locker) {
			Value result = cache.get(key);
			if (result == null) {
				result = onMissValue;
				if (result != null)
					cache.put(key, result);
			}
			return result;
		}
	}

	/** Returns the value from cache or throws an exception. Useful only for test? */
	public Value get(Key key) {
		synchronized (locker) {
			Value result = cache.get(key);
			if (result == null) {
				throw new IllegalStateException("The value should be in cache.");
			}
			return result;
		}
	}

	public int hitsCounter() {
		synchronized (locker) {
			return cache.hitsCounter();
		}
	}

	public int requestsCounter() {
		synchronized (locker) {
			return cache.requestsCounter();
		}
	}
}

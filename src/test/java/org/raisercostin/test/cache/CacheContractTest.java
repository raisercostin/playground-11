package org.raisercostin.test.cache;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.Test;
import org.raisercostin.test.cache.storage.MemoryStorageStrategy;
import org.raisercostin.test.cache.strategy.LeastRecentlyUsedCacheStrategy;
import org.raisercostin.test.cache.strategy.RandomCacheStrategy;

public class CacheContractTest {
	@Test
	public void testBasicOperations() {
		SimpleCache<Integer, String> cacheEngine = createLRUCache();
		cache = CacheTemplate.create(cacheEngine, null);
		assertEquals(false, cache.containsKey("1"));
		cache.put("1", "first");
		assertEquals(true, cache.containsKey("1"));
		assertEquals("first", cache.get("1"));
	}

	//
	// @Test
	// public void testMultipleLevels() {
	// CascadedCache<String, String> cache = new CascadedCache<String, String>(
	// new SimpleCache<String, String>());
	// assertEquals(false, cache.containsKey("1"));
	// cache.put("1", "first");
	// assertEquals(true, cache.containsKey("1"));
	// assertEquals("first", cache.get("1"));
	// }

	private SimpleCache<Integer, String> createLRUCache() {
		return new SimpleCache<Integer, String>(
				new MemoryStorageStrategy<Integer, String>(),
				new LeastRecentlyUsedCacheStrategy<Integer>(5));
	}
}

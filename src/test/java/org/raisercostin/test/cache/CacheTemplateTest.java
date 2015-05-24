package org.raisercostin.test.cache;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CacheTemplateTest {
	@Test
	public void testBasicOperations() {
		CacheTemplate<Integer, String> cache = CacheTemplate.createSimpleMemoryLRU(5);
		assertEquals("first", cache.getOr(1, "first"));
		assertEquals("first", cache.get(1));
	}

	@Test
	public void testBasicRandomStrategy() {
		CacheTemplate<Integer, String> cache = CacheTemplate.createSimpleMemoryRandom(3);
		Integer[] vals = exerciseCache(cache);
		assertEquals(vals.length,cache.requestsCounter());
		assertEquals(3,cache.hitsCounter());
	}

	@Test
	public void testBasicLRU() {
		CacheTemplate<Integer, String> cache = CacheTemplate.createSimpleMemoryLRU(3);
		Integer[] vals = exerciseCache(cache);
		assertEquals(vals.length,cache.requestsCounter());
		assertEquals(5,cache.hitsCounter());
	}

	private Integer[] exerciseCache(CacheTemplate<Integer, String> cache) {
		Integer[] vals = { 7, 0, 1, 2, 0, 3, 0, 4, 2, 3, 0, 3, 2, 1, 2 };
		for (Integer i : vals) {
			cache.getOr(i, "value" + i);
		}
		return vals;
	}
}

package org.raisercostin.test.cache;

import static org.junit.Assert.assertEquals;

import java.util.Random;

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
		int requests = exerciseCache1(cache);
		assertEquals(requests, cache.requestsCounter());
		assertEquals(3, cache.hitsCounter());
		requests += exerciseCache2(cache);
		assertEquals(requests, cache.requestsCounter());
		assertEquals(9, cache.hitsCounter());
	}

	@Test
	public void testBasicLRU() {
		CacheTemplate<Integer, String> cache = CacheTemplate.createSimpleMemoryLRU(3);
		int requests = exerciseCache1(cache);
		assertEquals(requests, cache.requestsCounter());
		assertEquals(5, cache.hitsCounter());
		requests += exerciseCache2(cache);
		assertEquals(requests, cache.requestsCounter());
		assertEquals(14, cache.hitsCounter());
	}

	private int exerciseCache1(CacheTemplate<Integer, String> cache) {
		Integer[] vals = { 7, 0, 1, 2, 0, 3, 0, 4, 2, 3, 0, 3, 2, 1, 2 };
		for (Integer i : vals) {
			cache.getOr(i, "value" + i);
		}
		return vals.length;
	}

	private int exerciseCache2(CacheTemplate<Integer, String> cache) {
		Random r = new Random(11);
		for (int i = 0; i < 100; i++) {
			Integer j = r.nextInt(50);
			cache.getOr(j, "value" + j);
		}
		return 100;
	}
}

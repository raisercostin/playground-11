package org.raisercostin.test.cache;

import static org.junit.Assert.assertEquals;

import java.util.Random;
import java.util.concurrent.*;

import org.junit.Test;

public class CacheTemplateTest {
	private final static org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(CacheTemplateTest.class);
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
		requests += exerciseCache2(cache,100);
		assertEquals(requests, cache.requestsCounter());
		assertEquals(9, cache.hitsCounter());
	}

	@Test
	public void testBasicLRU() {
		CacheTemplate<Integer, String> cache = CacheTemplate.createSimpleMemoryLRU(3);
		int requests = exerciseCache1(cache);
		assertEquals(requests, cache.requestsCounter());
		assertEquals(5, cache.hitsCounter());
		requests += exerciseCache2(cache,100);
		assertEquals(requests, cache.requestsCounter());
		assertEquals(14, cache.hitsCounter());
	}

	@Test
	public void testMultithreadingLRU() throws InterruptedException {
		final CacheTemplate<Integer, String> cache = CacheTemplate.createSimpleMemoryLRU(3);
		final Random r = new Random(10);
		final int cacheRequestsPerThread = 10;
		int threads = 5;
		ExecutorService pool = Executors.newFixedThreadPool(threads);
		for (int i = 0; i < threads; i++) {
			pool.submit(new Runnable() {
				@Override
				public void run() {
					exerciseCache3(cache,r,cacheRequestsPerThread);
					LOG.debug("thread finished.");
				}
			});
		}
		pool.shutdown();
		assertEquals(true,pool.awaitTermination(10, TimeUnit.SECONDS));
		assertEquals(threads * cacheRequestsPerThread, cache.requestsCounter());
		//is not deterministic since we can't control the thread switch
		//assertEquals(4, cache.hitsCounter());
	}

	private int exerciseCache1(CacheTemplate<Integer, String> cache) {
		Integer[] vals = { 7, 0, 1, 2, 0, 3, 0, 4, 2, 3, 0, 3, 2, 1, 2 };
		for (Integer i : vals) {
			cache.getOr(i, "value" + i);
		}
		return vals.length;
	}

	private int exerciseCache2(CacheTemplate<Integer, String> cache, int cacheRequests) {
		exerciseCache3(cache, new Random(11),cacheRequests);
		return cacheRequests;
	}

	private void exerciseCache3(CacheTemplate<Integer, String> cache, Random r, int cacheRequests) {
		for (int i = 0; i < cacheRequests; i++) {
			Integer j = r.nextInt(50);
			cache.getOr(j, "value" + j);
		}
	}
}

package org.raisercostin.test.cache;

import static org.junit.Assert.*;

import java.io.File;
import java.util.*;

import org.junit.Test;
import org.raisercostin.test.cache.storage.DiskStorageStrategy;
import org.raisercostin.test.cache.storage.MemoryStorageStrategy;
import org.raisercostin.test.cache.strategy.*;

public class SimpleCacheTest {
	private final static org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(SimpleCacheTest.class);
	private DiskStorageStrategy<Integer> storage;
	private SimpleCache<Integer, String> cache;
	private CacheTemplate<Integer, String> c;
	private Map<Integer, String> data;

	@Test
	public void test() {
		SimpleCache<Integer, String> cache = new SimpleCache<Integer, String>(
				new MemoryStorageStrategy<Integer, String>(), new RandomCacheStrategy<Integer>(5, new Random(10)));
		assertFalse(cache.containsKey(1));
		assertEquals(null, cache.get(1));
		cache.put(1, "value1");
		assertTrue(cache.containsKey(1));
		assertEquals("value1", cache.get(1));
	}

	@Test
	public void extensiveTest() {
		int SEED = 10;
		int CACHE_MAX_ENTRIES = 15;
		int DATA_SIZE = 2123;
		testInvariants("storage1", createRR(CACHE_MAX_ENTRIES, SEED), SEED, CACHE_MAX_ENTRIES, DATA_SIZE);
		testInvariants("storage2", createLRU(CACHE_MAX_ENTRIES), SEED, CACHE_MAX_ENTRIES, DATA_SIZE);
	}

	@Test
	public void cacheContainsAllDataAndNoDuplicates() {
		int SEED = 10;
		int CACHE_MAX_ENTRIES = 16;
		int DATA_SIZE = CACHE_MAX_ENTRIES - 1;
		testInvariants("storage3", new LeastRecentlyUsedCacheStrategy<Integer>(CACHE_MAX_ENTRIES), SEED,
				CACHE_MAX_ENTRIES, DATA_SIZE);
		for (int i = 0; i < DATA_SIZE; i++) {
			int index = i;
			LOG.debug("{}", index);
			assertEquals(data.get(index), c.getOrLoad(index));
			// invariants
			assertTrue("Storage has " + storage.size() + " entries and should have a maximum of " + CACHE_MAX_ENTRIES,
					storage.size() <= CACHE_MAX_ENTRIES);
		}
	}

	private <Key> void testInvariants(String name, CacheStrategy<Integer> cacheStrategy, int SEED,
			int CACHE_MAX_ENTRIES, int DATA_SIZE) {
		storage = new DiskStorageStrategy<Integer>(new File("target/" + name));
		storage.clear();
		cache = new SimpleCache<Integer, String>(storage, cacheStrategy);

		Random r = new Random(SEED);
		data = createUncachedData(DATA_SIZE);
		c = CacheTemplate.create(cache, data);
		for (int i = 0; i < 100; i++) {
			int index = r.nextInt(DATA_SIZE);
			assertEquals(data.get(index), c.getOrLoad(index));
			// invariants
			assertTrue("Storage has " + storage.size() + " entries and should be fewer than the maximum cache entries "
					+ CACHE_MAX_ENTRIES, storage.size() <= CACHE_MAX_ENTRIES);
			// invariants
			assertTrue("Storage has " + storage.size()
					+ " entries and should be fewer than the maximum data size entries " + DATA_SIZE,
					storage.size() <= DATA_SIZE);
		}
	}

	private RandomCacheStrategy<Integer> createRR(int maxEntries, int seed) {
		return new RandomCacheStrategy<Integer>(maxEntries, new Random(seed));
	}

	private CacheStrategy<Integer> createLRU(int maxEntries) {
		return new LeastRecentlyUsedCacheStrategy<Integer>(maxEntries);
	}

	private Map<Integer, String> createUncachedData(int max) {
		HashMap<Integer, String> result = new HashMap<Integer, String>();
		for (int i = 0; i < max; i++) {
			result.put(i, "value" + i);
		}
		return result;
	}
}

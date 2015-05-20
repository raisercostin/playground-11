package org.raisercostin.test.cache;

import static org.junit.Assert.*;

import java.io.File;
import java.util.*;

import org.junit.Test;
import org.raisercostin.test.cache.replacement.*;
import org.raisercostin.test.cache.storage.DiskStorageStrategy;
import org.raisercostin.test.cache.storage.MemoryStorageStrategy;

public class SimpleCacheTest {
    private DiskStorageStrategy<Integer> storage;
    private SimpleCache<Integer, String> cache;
    private CacheTemplate<Integer, String> c;
    private Map<Integer, String> data;

    @Test
    public void test() {
	SimpleCache<Integer, String> cache = new SimpleCache<Integer, String>(
		new MemoryStorageStrategy<Integer, String>(),
		new RandomCacheStrategy<Integer>(5, new Random(10)));
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
	testInvariants(createRR(SEED, CACHE_MAX_ENTRIES), SEED,
		CACHE_MAX_ENTRIES, DATA_SIZE);
    }

    @Test
    public void cacheContainsAllDataAndNoDuplicates() {
	int SEED = 10;
	int CACHE_MAX_ENTRIES = 16;
	int DATA_SIZE = CACHE_MAX_ENTRIES - 1;
	testInvariants(new LeastRecentlyUsedCacheStrategy<Integer>(
		CACHE_MAX_ENTRIES), SEED, CACHE_MAX_ENTRIES, DATA_SIZE);
	for (int i = 0; i < DATA_SIZE; i++) {
	    int index = i;
	    System.out.println(index);
	    assertEquals(data.get(index), c.getOrCompute(index));
	    // invariants
	    assertTrue("Storage has " + storage.size()
		    + " entries and should have a maximum of "
		    + CACHE_MAX_ENTRIES, storage.size() <= CACHE_MAX_ENTRIES);
	}
	// assertEquals("All values should be in storage since cache is bigger than all data and all data was retrieved.",DATA_SIZE,
	// storage.size());
    }

    private <Key> void testInvariants(CacheStrategy<Integer> replacement,
	    int SEED, int CACHE_MAX_ENTRIES, int DATA_SIZE) {
	storage = new DiskStorageStrategy<Integer>(new File("target/storage2"));
	storage.clear();
	cache = new SimpleCache<Integer, String>(storage, replacement);

	Random r = new Random(SEED);
	data = createUncachedData(DATA_SIZE);
	c = CacheTemplate.create(cache, data);
	for (int i = 0; i < 100; i++) {
	    int index = r.nextInt(DATA_SIZE);
	    assertEquals(data.get(index), c.getOrCompute(index));
	    // invariants
	    assertTrue(
		    "Storage has "
			    + storage.size()
			    + " entries and should be fewer than the maximum cache entries "
			    + CACHE_MAX_ENTRIES,
		    storage.size() <= CACHE_MAX_ENTRIES);
	    // invariants
	    assertTrue(
		    "Storage has "
			    + storage.size()
			    + " entries and should be fewer than the maximum data size entries "
			    + DATA_SIZE, storage.size() <= DATA_SIZE);
	}
    }

    private RandomCacheStrategy<Integer> createRR(int SEED,
	    int CACHE_MAX_ENTRIES) {
	return new RandomCacheStrategy<Integer>(CACHE_MAX_ENTRIES,
		new Random(SEED));
    }

    private Map<Integer, String> createUncachedData(int max) {
	HashMap<Integer, String> result = new HashMap<Integer, String>();
	for (int i = 0; i < max; i++) {
	    result.put(i, "value" + i);
	}
	return result;
    }
}

package org.raisercostin.test.cache;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;
import org.raisercostin.test.cache.replacement.RandomReplacementStrategy;
import org.raisercostin.test.cache.storage.MemoryStorageStrategy;

public class SimpleCacheTest {
    @Test
    public void test() {
	SimpleCache<Integer, String> cache = new SimpleCache<Integer, String>(
		new MemoryStorageStrategy<Integer, String>(),
		new RandomReplacementStrategy<Integer>(5, new Random(10)));
	assertFalse(cache.containsKey(1));
	assertEquals(null, cache.get(1));
	cache.put(1, "value1");
	assertTrue(cache.containsKey(1));
	assertEquals("value2", cache.get(1));
    }
}

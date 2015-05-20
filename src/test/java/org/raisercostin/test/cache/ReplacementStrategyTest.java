package org.raisercostin.test.cache;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.Test;
import org.raisercostin.test.cache.replacement.RandomCacheStrategy;
import org.raisercostin.test.cache.replacement.CacheStrategy;

public class ReplacementStrategyTest {
    @Test
    public void testFifoReplacementOperations() {
	CacheStrategy<String> rep = new RandomCacheStrategy<String>(5, new Random(10));
	assertEquals(null, rep.update("1"));
	assertEquals(null, rep.update("2"));
	assertEquals("1", rep.update("3"));
	assertEquals("2", rep.update("2"));
	assertEquals(null, rep.update("4"));
	assertEquals("4", rep.update("5"));
	assertEquals(null, rep.update("6"));
	assertEquals("3", rep.update("7"));
	assertEquals("5", rep.update("8"));
	assertEquals(null, rep.update("9"));
	assertEquals("7", rep.update("10"));
	assertEquals("9", rep.update("11"));
	assertEquals("8", rep.update("12"));
	assertEquals("10", rep.update("13"));
    }
}

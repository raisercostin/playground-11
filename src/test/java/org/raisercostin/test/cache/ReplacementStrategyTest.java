package org.raisercostin.test.cache;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.Test;
import org.raisercostin.test.cache.replacement.RandomCacheStrategy;
import org.raisercostin.test.cache.replacement.CacheStrategy;

public class ReplacementStrategyTest {
    @Test
    public void testFifoReplacementOperations() {
	CacheStrategy<Integer> rep = new RandomCacheStrategy<Integer>(5,
		new Random(10));
	assertEq(null, rep.update(1));
	assertEq(null, rep.update(2));
	assertEq(1, rep.update(3));
	assertEq(2, rep.update(2));
	assertEq(2, rep.update(4));
	assertEq(null, rep.update(5));
	assertEq(5, rep.update(6));
	assertEq(null, rep.update(7));
	assertEq(3, rep.update(8));
	assertEq(6, rep.update(9));
	assertEq(null, rep.update(10));
	assertEq(8, rep.update(11));
	assertEq(10, rep.update(12));
	assertEq(9, rep.update(13));
    }

    //needed for automatic boxing
    private void assertEq(Integer expected, Integer actual) {
	assertEquals(expected, actual);
    }
}

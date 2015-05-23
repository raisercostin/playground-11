package org.raisercostin.test.cache.replacement;

import static org.junit.Assert.*;

import org.junit.Test;

public class LeastRecentlyUsedCacheStrategyTest {

    @Test
    public void testHits() {
	// @see https://www.youtube.com/watch?v=4wVp97-uqr0 - for a small test
	LeastRecentlyUsedCacheStrategy<Integer> cache = new LeastRecentlyUsedCacheStrategy<Integer>(
		3);
	//@formatter:off
	Integer[][] val = {
		{7,null}
		, {0,null}
        	, {1,null}
        	, {2,7}
        	, {0,0}
        	, {3,1}
        	, {0,0}
        	, {4,2}
        	, {2,3}
        	, {3,0}
        	, {0,4}
        	, {3,3}
        	, {2,2}
        	, {1,0}
        	, {2,2}
	};
	//@formatter:on
	for (int i = 0; i < val.length; i++) {
	    String before = cache.state();
	    Integer actual = cache.update(val[i][0]);
	    String after = cache.state();
	    //System.out.println("before="+before);
	    System.out.println("after ="+after);
	    assertEquals("Key "+val[i][0]+" should replace key "+val[i][1] + " but replaced "+actual,val[i][1], actual);
	}
	assertEquals(15, cache.countAll());
	assertEquals(4, cache.countHits());
    }
}
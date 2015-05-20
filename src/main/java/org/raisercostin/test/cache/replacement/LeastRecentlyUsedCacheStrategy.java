package org.raisercostin.test.cache.replacement;

import java.util.Random;

/**
 * Random Replacement (RR) Randomly selects a candidate item and discards it to
 * make space when necessary. This algorithm does not require keeping any
 * information about the access history. For its simplicity, it has been used in
 * ARM processors.[5] It admits efficient stochastic simulation.[6]
 *
 * @author costin
 * @param <Key>
 */
public class LeastRecentlyUsedCacheStrategy<Key> implements CacheStrategy<Key> {
    private Key[] keys;

    @SuppressWarnings("unchecked")
    public LeastRecentlyUsedCacheStrategy(int maxEntries) {
	this.keys = (Key[]) new Object[maxEntries];
    }

    @Override
    public Key update(Key key) {
	return null;
    }
}

package org.raisercostin.test.cache.replacement;

/**
 * A cache replacement strategy.
 *
 * From wikipedia: In computing, cache algorithms (also frequently called
 * replacement algorithms or replacement policies) are optimizing instructions –
 * or algorithms – that a computer program or a hardware-maintained structure
 * can follow, in order to manage a cache of information stored on the computer.
 * When the cache is full, the algorithm must choose which items to discard to
 * make room for the new ones.
 *
 * @see http://dhruvbird.com/lfu.pdf - LFU in O(1)
 *
 * @see http://en.wikipedia.org/wiki/Cache_algorithms
 * @author costin
 *
 */
public interface CacheStrategy<Key> {
    /**
     * Declares a used key and returns the replaced key.
     * @param key
     * @return null if the update doesn't return any other key
     */
    Key update(Key key);
}

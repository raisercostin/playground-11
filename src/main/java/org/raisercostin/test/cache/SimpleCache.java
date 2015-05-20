package org.raisercostin.test.cache;

import org.raisercostin.test.cache.replacement.CacheStrategy;
import org.raisercostin.test.cache.storage.StorageStrategy;

/**
 * Class that have the responsibility of storing/removing the values according
 * to the given replacement strategy.
 */
public class SimpleCache<Key, Value> implements Cache<Key, Value> {
    public final StorageStrategy<Key, Value> storage;
    public final CacheStrategy<Key> replacement;

    public SimpleCache(StorageStrategy<Key, Value> storage,
	    CacheStrategy<Key> replacement) {
	this.storage = storage;
	this.replacement = replacement;
    }

    @Override
    public void put(Key key, Value value) {
	// if (!storage.containsKey(key)) {
	Key replacedKey = replacement.update(key);
	if (replacedKey != null) {
	    System.out.println("add " + key + " replacing " + replacedKey);
	    storage.remove(replacedKey);
	} else {
	    System.out.println("add " + key);
	}
	// }
	storage.save(key, value);
	System.out.println("      size=" + storage.size());
    }

    @Override
    public Value get(Key key) {
	return storage.loadOr(key, null);
    }

    @Override
    public boolean containsKey(Key key) {
	return storage.containsKey(key);
    }

    @Override
    public Value remove(Key key) {
	Value result = get(key);
	storage.remove(key);
	return result;
    }

    @Override
    public boolean isEmpty() {
	return storage.size() == 0;
    }

    @Override
    public int size() {
	return storage.size();
    }

    @Override
    public void clear() {
	storage.clear();
    }
}

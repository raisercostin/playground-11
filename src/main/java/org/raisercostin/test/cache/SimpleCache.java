package org.raisercostin.test.cache;

import org.raisercostin.test.cache.replacement.RandomReplacementStrategy;
import org.raisercostin.test.cache.replacement.ReplacementStrategy;
import org.raisercostin.test.cache.storage.MemoryStorageStrategy;
import org.raisercostin.test.cache.storage.StorageStrategy;

public class SimpleCache<Key, Value> implements Cache<Key, Value> {
    private StorageStrategy<Key, Value> storage;
    private ReplacementStrategy<Key> replacement;

    public SimpleCache(StorageStrategy<Key, Value> storage, ReplacementStrategy<Key> replacement) {
	this.storage = storage;
	this.replacement = replacement;
    }

    @Override
    public void put(Key key, Value value) {
	Key replacedKey = replacement.update(key);
	storage.remove(key);
	storage.save(key, value);
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
	return storage.isEmpty();
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

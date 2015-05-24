package org.raisercostin.test.cache;

import org.raisercostin.test.cache.storage.StorageStrategy;
import org.raisercostin.test.cache.strategy.CacheStrategy;
import org.slf4j.LoggerFactory;

/**
 * Class that have the responsibility of storing/removing the values according to the given replacement strategy.
 */
public class SimpleCache<Key, Value> implements ObservableCache<Key, Value> {
	private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(SimpleCache.class);
	public final StorageStrategy<Key, Value> storage;
	public final CacheStrategy<Key> strategy;
	private int hits = 0;
	private int requests = 0;

	public SimpleCache(StorageStrategy<Key, Value> storage, CacheStrategy<Key> replacement) {
		replacement.initialize(storage);
		this.storage = storage;
		this.strategy = replacement;
	}

	@Override
	public Value get(Key key) {
		requests++;
		Key replacedKey = strategy.update(key);
		boolean hit = replacedKey == key;
		LOG.debug("{}: add {} replacing {} => {} {}", this, key, replacedKey, strategy.displayState(), hit ? "hit" : "");
		if (hit) {
			hits++;
		} else if (replacedKey != null) {
			storage.remove(replacedKey);
		}
		return storage.loadOr(key, null);
	}

	@Override
	public boolean containsKey(Key key) {
		return storage.containsKey(key);
	}

	@Override
	public void put(Key key, Value value) {
		storage.save(key, value);
	}

	@Override
	public Value remove(Key key) {
		throw new IllegalAccessError(
				"Method should not be exposed. Is an internal cache operation done on storage, goverened by the replacement caching strategy.");
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

	@Override
	public int hitsCounter() {
		return hits;
	}

	@Override
	public int requestsCounter() {
		return requests;
	}
}

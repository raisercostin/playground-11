package org.raisercostin.test.cache;

/**
 * Class that delegate to next cache level if operations miss.
 */
public class CascadedCache<Key, Value> implements ObservableCache<Key, Value> {
	private ObservableCache<Key, Value> first;
	private ObservableCache<Key, Value> second;

	public CascadedCache(ObservableCache<Key, Value> first, ObservableCache<Key, Value> second) {
		this.first = first;
		this.second = second;
	}

	@Override
	public void put(Key key, Value value) {
		// how do you cascade put from CacheTemplate?
		first.put(key, value);
		second.put(key, value);
	}

	@Override
	public Value get(Key key) {
		Value value1 = first.get(key);
		if (value1 != null)
			return value1;
		else {
			return second.get(key);
		}
	}

	@Override
	public boolean containsKey(Key key) {
		return first.containsKey(key) || second.containsKey(key);
	}

	@Override
	public Value remove(Key key) {
		throw new IllegalAccessError(
				"Method should not be exposed. Is an internal cache operation done on storage, goverened by the replacement caching strategy.");
	}

	@Override
	public boolean isEmpty() {
		return first.isEmpty() && second.isEmpty();
	}

	@Override
	public int size() {
		throw new IllegalAccessError(
				"Method should not be exposed. Is an internal cache operation done on storage. For multiple levels cache it doesn't have too much sense.");
		// return first.size() + second.size();
	}

	@Override
	public void clear() {
		//throw new IllegalAccessError("Method should not be exposed. Is an internal cache operation done on storage.");
		first.clear();
		second.clear();
	}

	@Override
	public int hitsCounter() {
		return first.hitsCounter()+second.hitsCounter();
	}

	@Override
	public int requestsCounter() {
		return first.requestsCounter();
	}

}

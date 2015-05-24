package org.raisercostin.test.cache.storage;

public interface StorageStrategy<K, V> {
	void save(K key, V value);

	V loadOr(K key, V defaultValue);

	void remove(K key);

	boolean containsKey(K key);

	int size();

	void clear();
}

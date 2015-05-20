package org.raisercostin.test.cache;

public interface Storage<K,V> {
	void save(K key, V value);
	V loadOr(K key, V defaultValue);
}

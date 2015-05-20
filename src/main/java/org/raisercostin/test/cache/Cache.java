package org.raisercostin.test.cache;

public interface Cache<K, V> {
    void put(K key, V value);

    V get(K key);

    boolean containsKey(K key);

    V remove(K key);

    boolean isEmpty();

    int size();

    void clear();
}
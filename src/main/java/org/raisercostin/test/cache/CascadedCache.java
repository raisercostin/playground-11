package org.raisercostin.test.cache;

/**
 * Class that delegate to next cache level if operations miss.
 */
public class CascadedCache<K, V> implements Cache<K, V> {
	private Cache<K, V> first;
	private Cache<K, V> second;

	public CascadedCache(Cache<K, V> first, Cache<K, V> second) {
		this.first = first;
		this.second = second;
	}

	@Override
	public void put(K key, V value) {
		// TODO Auto-generated method stub
	}

	@Override
	public V get(K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean containsKey(K key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public V remove(K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

}

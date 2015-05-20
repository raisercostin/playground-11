package org.raisercostin.test.cache;

import java.util.HashMap;
import java.util.Map;

public class SimpleCache<T1, T2> implements Cache<T1,T2> {
	private Map<T1,T2> map=new HashMap<T1,T2>();

	@Override
	public void put(T1 key, T2 value) {
		map.put(key, value);
	}

	@Override
	public T2 get(T1 key) {
		return map.get(key);
	}

	@Override
	public boolean containsKey(T1 key) {
		return map.containsKey(key);
	}

	@Override
	public T2 remove(T1 key) {
		return map.remove(key);
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public void clear() {
		map.clear();
	}
}

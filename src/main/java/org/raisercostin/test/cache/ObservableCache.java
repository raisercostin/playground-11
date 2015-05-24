package org.raisercostin.test.cache;

public interface ObservableCache<K,V> extends Cache<K,V>{
	/**Method to observe the hits of current cache.*/
	int hitsCounter();
	int requestsCounter();
}

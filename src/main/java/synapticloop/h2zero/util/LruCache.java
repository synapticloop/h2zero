package synapticloop.h2zero.util;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class LruCache<K,V> {
	private Map<K,V> cache;
	private AbstractQueue<K> queue;
	private LruCacheStatistics lruCacheStatistics = null;
	private int size = 0;

	public LruCache(int size) {
		this.size = size;
		cache = new ConcurrentHashMap<K,V>(size);
		queue = new ConcurrentLinkedQueue<K>();
		this.lruCacheStatistics = new LruCacheStatistics(size);
	}

	public boolean containsKey(K key) {
		return(cache.containsKey(key));
	}

	public V get(K key) {
		//Recently accessed, hence move it to the tail
		queue.remove(key);
		queue.add(key);
		lruCacheStatistics.incrementHitCount();
		return cache.get(key);
	}

	/**
	 * For testing - do not update the accessed status
	 * 
	 * @param key the key to look up
	 * @return the value of the key
	 */
	public V getSilent(K key) {
		return cache.get(key);
	}

	public void put(K key, V value) {
		//ConcurrentHashMap doesn't allow null key or values
		if(key == null || value == null) throw new NullPointerException();

		if(cache.containsKey(key)) {
			queue.remove(key);
			lruCacheStatistics.incrementHitCount();
		} else {
			lruCacheStatistics.incrementMissCount();
			lruCacheStatistics.incrementPutCount();
		}

		if(queue.size() >= size) {
			K lruKey = queue.poll();
			if(lruKey != null) {
				cache.remove(lruKey);
				lruCacheStatistics.incrementEvictionCount();
			}
		}

		queue.add(key);
		cache.put(key,value);
	}

	public LruCacheStatistics getLruCacheStatistics() { return lruCacheStatistics; }

	// TODO - probably remove
	@Override
	public synchronized String toString() {
		Iterator<K> iterator = queue.iterator();
		StringBuilder stringBuilder = new StringBuilder();

		while (iterator.hasNext()) {
			K key = iterator.next();
			stringBuilder.append(key + ":" + this.getSilent(key) + "\n");
		}
		return(stringBuilder.toString());
	}
}
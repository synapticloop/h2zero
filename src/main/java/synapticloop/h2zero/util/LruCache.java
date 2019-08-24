package synapticloop.h2zero.util;

/*
 * Copyright (c) 2012-2019 synapticloop.
 * All rights reserved.
 *
 * This source code and any derived binaries are covered by the terms and
 * conditions of the Licence agreement ("the Licence").  You may not use this
 * source code or any derived binaries except in compliance with the Licence.
 * A copy of the Licence is available in the file named LICENCE shipped with
 * this source code or binaries.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * Licence for the specific language governing permissions and limitations
 * under the Licence.
 */

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * This is a light-weight implementation of the Least Recently Used cache
 * 
 * @author synapticloop
 *
 * @param <K> The key type
 * @param <V> The value type
 */
public class LruCache<K,V> {
	private static final int DEFAULT_CACHE_SIZE = 512;
	private Map<K,V> cache = null;
	private AbstractQueue<K> queue = null;
	private LruCacheStatistics lruCacheStatistics = null;
	private int size = 0;

	/**
	 * Create a LruCache with the default size (DEFAULT_CACHE_SIZE = 512)
	 */
	public LruCache() {
		this(DEFAULT_CACHE_SIZE);
	}

	/**
	 * Create a LruCache with a particular size
	 * 
	 * @param size the size of the cache
	 */
	public LruCache(int size) {
		this.size = size;
		cache = new ConcurrentHashMap<K,V>(size);
		queue = new ConcurrentLinkedQueue<K>();
		this.lruCacheStatistics = new LruCacheStatistics(size);
	}

	/**
	 * Determine whether the cache contains a particular key
	 * 
	 * @param key the key to check for existance
	 * 
	 * @return whether the key is within the cache
	 */
	public boolean containsKey(K key) {
		return(cache.containsKey(key));
	}

	public V get(K key) {
		//Recently accessed, hence move it to the tail
		queue.remove(key);
		queue.add(key);
		
		V cacheValue = cache.get(key);
		if(null != cacheValue) {
			lruCacheStatistics.incrementHitCount();
		} else {
			lruCacheStatistics.incrementMissCount();
		}
		return cacheValue;
	}

	/**
	 * For testing - do not update the cache statistics
	 * 
	 * @param key the key to look up
	 * @return the value of the key
	 */
	public V getSilent(K key) {
		return cache.get(key);
	}

	/**
	 * Place a value into the cache and evict, or re-shuffle the ordering.  If the 
	 * key doesn't exist, increment the miss and put counts. 
	 * 
	 * @param key The key to use
	 * @param value The value to use
	 */
	public void put(K key, V value) {
		//ConcurrentHashMap doesn't allow null key or values
		if(key == null || value == null) throw new NullPointerException();

		if(cache.containsKey(key)) {
			queue.remove(key);
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

	/**
	 * Get the statistics object for this cache, this is mainly for reporting
	 * and statistics
	 * 
	 * @return The lru cache statistics
	 */
	public LruCacheStatistics getLruCacheStatistics() { return lruCacheStatistics; }

	@Override
	public synchronized String toString() {
		Iterator<K> iterator = queue.iterator();
		StringBuilder stringBuilder = new StringBuilder();

		while (iterator.hasNext()) {
			K key = iterator.next();
			stringBuilder.append("{ ");
			stringBuilder.append(key);
			stringBuilder.append(":");
			stringBuilder.append(this.getSilent(key));
			stringBuilder.append(" }");
			if(iterator.hasNext()) {
				stringBuilder.append(", ");
			}
		}
		return(stringBuilder.toString());
	}
}

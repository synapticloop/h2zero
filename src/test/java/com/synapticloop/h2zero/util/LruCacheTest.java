package com.synapticloop.h2zero.util;

import static org.junit.Assert.*;

import com.synapticloop.h2zero.util.LruCache;
import org.junit.Before;
import org.junit.Test;

public class LruCacheTest {
	private LruCache<Integer, Integer> lruCache = null;

	@Before
	public void setup() {
		lruCache = new LruCache<Integer, Integer>(3);
	}

	@Test
	public void testCacheEviction() {
		lruCache.put(1, 1);
		assertEquals(1, lruCache.getLruCacheStatistics().getMissCount());
		assertEquals(1, lruCache.getLruCacheStatistics().getPutCount());

		lruCache.put(2, 2);
		assertEquals(2, lruCache.getLruCacheStatistics().getMissCount());
		assertEquals(2, lruCache.getLruCacheStatistics().getPutCount());

		lruCache.put(3, 3);
		assertEquals(3, lruCache.getLruCacheStatistics().getMissCount());
		assertEquals(3, lruCache.getLruCacheStatistics().getPutCount());

		lruCache.put(4, 4);
		assertEquals(4, lruCache.getLruCacheStatistics().getMissCount());
		assertEquals(4, lruCache.getLruCacheStatistics().getPutCount());

		lruCache.put(4, 4);
		assertEquals(4, lruCache.getLruCacheStatistics().getMissCount());
		assertEquals(4, lruCache.getLruCacheStatistics().getPutCount());


		assertFalse(lruCache.containsKey(1));
		lruCache.get(1);

		assertEquals(1, lruCache.getLruCacheStatistics().getEvictionCount());
		assertEquals(4, lruCache.getLruCacheStatistics().getPutCount());
		assertEquals(0, lruCache.getLruCacheStatistics().getHitCount());

		lruCache.get(4);
		assertEquals(1, lruCache.getLruCacheStatistics().getHitCount());
	}

	@Test
	public void testToStringOrdering() {
		lruCache.put(1, 1);
		lruCache.put(2, 2);
		lruCache.put(3, 3);
		assertEquals("{ 1:1 }, { 2:2 }, { 3:3 }", lruCache.toString());

		lruCache.get(2);
		assertEquals("{ 1:1 }, { 3:3 }, { 2:2 }", lruCache.toString());

		lruCache.get(1);
		assertEquals("{ 3:3 }, { 2:2 }, { 1:1 }", lruCache.toString());
	}

	@Test(expected = NullPointerException.class)
	public void testNullPut(){
		LruCache<String, String> cache = new LruCache<String, String>();
		cache.put(null, null);
	}
}

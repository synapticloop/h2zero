package synapticloop.h2zero.util;

/*
 * Copyright (c) 2012-2024 synapticloop.
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

/**
 * Light-weight POJO for cache statistics gathering
 * 
 * @author synapticloop
 *
 */
public class LruCacheStatistics {
	private int size = 0;
	private int putCount = 0;
	private int evictionCount = 0;
	private int hitCount = 0;
	private int missCount = 0;

	/**
	 * Create a new statistics object with a particular size that should match
	 * the size of the cache
	 * 
	 * @param size the size of the cache
	 */
	public LruCacheStatistics(int size) {
		this.size = size;
	}

	public int getSize() { return this.size; }
	public int getPutCount() { return this.putCount; }
	public int getEvictionCount() { return this.evictionCount; }
	public int getHitCount() { return this.hitCount; }
	public int getMissCount() { return this.missCount; }

	public void incrementPutCount() { this.putCount++; }
	public void incrementEvictionCount() { this.evictionCount++; }
	public void incrementHitCount() { this.hitCount++; }
	public void incrementMissCount() { this.missCount++; }
}

package synapticloop.h2zero.util;

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

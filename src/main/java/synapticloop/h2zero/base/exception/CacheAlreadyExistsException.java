package synapticloop.h2zero.base.exception;

/**
 * This exception is thrown if the cache already exists within the h2zero generation
 * 
 * @author synapticloop
 *
 */
public class CacheAlreadyExistsException extends Exception {
	private static final long serialVersionUID = 3435710822577749380L;

	public CacheAlreadyExistsException(String message) {
		super(message);
	}
}

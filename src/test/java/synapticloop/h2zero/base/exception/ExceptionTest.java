package synapticloop.h2zero.base.exception;

import org.junit.Test;

public class ExceptionTest {
	private CacheAlreadyExistsException cacheAlreadyExistsException;
	private CacheNotRegisteredException cacheNotRegisteredException;
	private H2ZeroException h2ZeroException;
	private H2ZeroPrimaryKeyException h2ZeroPrimaryKeyException;
	private H2ZeroFinderException h2ZeroFinderException;

	public void setup() {
	}

	@Test
	public void testExceptionCreation() {
		cacheAlreadyExistsException = new CacheAlreadyExistsException("message");
		cacheNotRegisteredException = new CacheNotRegisteredException("message");
		h2ZeroException = new H2ZeroException("message");
		h2ZeroFinderException = new H2ZeroFinderException("message");
		h2ZeroPrimaryKeyException = new H2ZeroPrimaryKeyException("message");
	}

}

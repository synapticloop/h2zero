package synapticloop.h2zero;

import org.junit.Test;

public class MainTest {
	private Main main;

	@Test
	public void testNullArguments() {
		Main.main(null);
	}

	@Test
	public void testTwoArguments() {
		Main.main(new String[]{"one", "two"});
	}

}

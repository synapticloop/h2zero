package synapticloop.h2zero;

import org.junit.Before;
import org.junit.Test;

public class MainTest {
	private Main main;

	@Before
	public void setup() {
		main = new Main();
	}

	@Test(expected=IllegalArgumentException.class)
	public void testNullArguments() {
		Main.main(null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testTwoArguments() {
		Main.main(new String[]{"one", "two"});
	}

}

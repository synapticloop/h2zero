package synapticloop.h2zero;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;

public class MainTestExternal {
	@Rule
	public final ExpectedSystemExit exit = ExpectedSystemExit.none();

	@Test
	public void testNullArguments() {
		exit.expectSystemExit();
		Main.main(null);
	}

	@Test
	public void testBadArguments() {
		exit.expectSystemExit();
		Main.main(new String[] {"one", "two"} );
	}

	@Test
	public void testGenerateMissingParams() {
		exit.expectSystemExit();
		Main.main(new String[] { "generate" });
	}
}

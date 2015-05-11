package synapticloop.h2zero.metrics;

import com.codahale.metrics.MetricRegistry;

public class Registry {
	private static final MetricRegistry registry = new MetricRegistry();

	public static MetricRegistry getRegistry() { return(registry); }
}

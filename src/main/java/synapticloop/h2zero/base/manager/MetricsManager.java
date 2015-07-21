package synapticloop.h2zero.base.manager;

import com.codahale.metrics.MetricRegistry;

public class MetricsManager {
	static final MetricRegistry metricsRegistry = new MetricRegistry();

	private MetricsManager() {}

	public static MetricRegistry getMetricsRegistry() { return metricsRegistry; }
}

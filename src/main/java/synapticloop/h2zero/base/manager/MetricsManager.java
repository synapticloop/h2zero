package synapticloop.h2zero.base.manager;

import com.codahale.metrics.MetricRegistry;

public class MetricsManager {
	static final MetricRegistry metricsRegistry = new MetricRegistry();

	public static MetricRegistry getMetricsregistry() { return metricsRegistry; }
}

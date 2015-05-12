package synapticloop.h2zero.base.servlet;

import java.io.IOException;
import java.util.Iterator;
import java.util.SortedMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import synapticloop.h2zero.base.manager.MetricsManager;

import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;

public class MuninMetricsServlet extends HttpServlet {
	private static final long serialVersionUID = -2162317801442120049L;

	@Override
	protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
		MetricRegistry metricsRegistry = MetricsManager.getMetricsRegistry();
		SortedMap<String,Counter> counters = metricsRegistry.getCounters();
		Iterator<String> iterator = counters.keySet().iterator();
		while (iterator.hasNext()) {
			String string = (String) iterator.next();
			System.out.println(string);
			
		}
	}
	

}

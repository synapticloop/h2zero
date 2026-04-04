package com.synapticloop.h2zero.generator.model.record;

public record DatabaseTypeProperty(
		String databaseType,
		String jdbcUrl,
		String initialJdbcUrl,
		int maxPoolSize,
		int minPoolSize,
		int initialPoolSize,
		int acquireIncrement,
		String driverClassName,
		boolean isLimitOffset){

	public DatabaseTypeProperty(
			String databaseType,
			String jdbcUrl,
			String initialJdbcUrl,
			String driverClassName,
			boolean isLimitOffset) {
		this(databaseType, jdbcUrl, initialJdbcUrl, 20, 20, 10, 3, driverClassName, isLimitOffset);

	}
}

package com.synapticloop.h2zero.generator.model.record;

public record DatabaseTypeProperty(String databaseType, String jdbcUrl, String initialJdbcUrl, String driverClassName, boolean isLimitOffset){
}

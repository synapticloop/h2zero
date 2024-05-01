package com.synapticloop.h2zero.model.record;

public record DatabaseTypeProperty(String databaseType, String jdbcUrl, String initialJdbcUrl, String driverClassName, boolean isLimitOffset){
}

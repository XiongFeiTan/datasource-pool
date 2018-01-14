package com.nemo.pool.jdbc;

public interface JdbcConfig {
     String DEFAULT_DRIVER_CLASS = "com.mysql.jdbc.Driver";
	 String DEFAULT_JDBC_URL = "jdbc:mysql://localhost:3306/test";
	 String DEFAULT_JDBC_USERNAME = "root";
	 String DEFAULT_JDBC_PASSWORD = "test";
	 String DRIVER_CLASS_PROPERTY = "driverClass";
	 String JDBC_URL_PROPERTY = "jdbcUrl";
	 String JDBC_USERNAME_PROPERTY = "username";
	 String JDBC_PASSWORD_PROPERTY = "password";
}

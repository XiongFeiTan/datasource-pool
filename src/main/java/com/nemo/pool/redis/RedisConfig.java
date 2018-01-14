package com.nemo.pool.redis;

public interface RedisConfig {
    int DEFAULT_TIMEOUT = 2000;
    int DEFAULT_DATABASE = 0;
    String DEFAULT_PASSWORD = null;
    String DEFAULT_CLIENTNAME = null;
    int DEFAULT_MAXATTE = 5;
    String ADDRESS_PROPERTY = "address";
    String TIMEOUT_PROPERTY = "timeout";
    String DATABASE_PROPERTY = "database";
    String PASSWORD_PROPERTY = "password";
    String CLIENTNAME_PROPERTY = "clientName";
    String CLUSTER_PROPERTY = "cluster";
    String MAXATTE_PROPERTY = "maxAttempts";
}

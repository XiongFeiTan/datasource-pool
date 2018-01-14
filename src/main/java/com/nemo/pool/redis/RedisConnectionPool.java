package com.nemo.pool.redis;

import com.nemo.pool.ConnectionPool;
import com.nemo.pool.PoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import java.util.Properties;

public class RedisConnectionPool implements ConnectionPool<Jedis> {
	private static final long serialVersionUID = 875648965821354698L;
	private final JedisPool pool;
    public RedisConnectionPool(final PoolConfig poolConfig,
                               final String host,
                               final int port,
                               final int connectionTimeout,
                               final int soTimeout,
                               final String password,
                               final int database,
                               final String clientName) {
        this.pool = new JedisPool(poolConfig, host, port, connectionTimeout, soTimeout, password, database, clientName, false, null, null, null);
    }

    public RedisConnectionPool(final PoolConfig poolConfig, final String host, final int port) {

        this(poolConfig, host, port,
                RedisConfig.DEFAULT_TIMEOUT,
                RedisConfig.DEFAULT_PASSWORD,
                RedisConfig.DEFAULT_DATABASE,
                RedisConfig.DEFAULT_CLIENTNAME);
    }

    public RedisConnectionPool(final String host, final int port) {
        this(new PoolConfig(), host, port);
    }



    public RedisConnectionPool(final Properties properties) {
        this(new PoolConfig(), properties);
    }

    public RedisConnectionPool(final PoolConfig poolConfig, final Properties properties) {
        this(poolConfig,
                properties.getProperty(RedisConfig.ADDRESS_PROPERTY).split(":")[0],
                Integer.parseInt(properties.getProperty(RedisConfig.ADDRESS_PROPERTY).split(":")[1]),
                Integer.parseInt(properties.getProperty(RedisConfig.TIMEOUT_PROPERTY, String.valueOf(RedisConfig.DEFAULT_TIMEOUT))),
                properties.getProperty(RedisConfig.PASSWORD_PROPERTY),
                Integer.parseInt(properties.getProperty(RedisConfig.DATABASE_PROPERTY, String.valueOf(RedisConfig.DEFAULT_DATABASE))),
                properties.getProperty(RedisConfig.CLIENTNAME_PROPERTY));

    }

    public RedisConnectionPool(final PoolConfig poolConfig,
                               final String host,
                               final int port,
                               final int timeout,
                               final String password,
                               final int database,
                               final String clientName) {

        this(poolConfig, host, port, timeout, timeout, password, database, clientName);
    }



    @Override
    public Jedis getConnection() {
        return pool.getResource();
    }

    @Override
    public void returnConnection(Jedis conn) {
        if (conn != null)
            conn.close();
    }

    @Override
    public void invalidateConnection(Jedis conn) {
        if (conn != null)
            conn.close();
    }

    public void close() {
        pool.close();
    }
}

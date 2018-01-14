package com.nemo.pool.redis;

import com.nemo.pool.ConnectionPool;
import com.nemo.pool.PoolConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class RedisClusterConnPool implements ConnectionPool<JedisCluster> {

	private static final long serialVersionUID = -7896584123654788958L;

	private final JedisCluster jedisCluster;
    public RedisClusterConnPool(final Set<HostAndPort> clusterNodes,
                                final int connectionTimeout,
                                final int soTimeout,
                                final int maxAttempts,
                                final String password,
                                final PoolConfig poolConfig) {
        jedisCluster = new JedisCluster(clusterNodes, connectionTimeout, soTimeout, maxAttempts, password, poolConfig);
    }

    public RedisClusterConnPool(final Set<HostAndPort> clusterNodes,
                                final int timeout) {
        this(clusterNodes, timeout, timeout);
    }
    public RedisClusterConnPool(final Set<HostAndPort> clusterNodes) {
        this(clusterNodes, RedisConfig.DEFAULT_TIMEOUT);
    }



    public RedisClusterConnPool(final Set<HostAndPort> clusterNodes,
                                final int connectionTimeout,
                                final int soTimeout) {
        this(clusterNodes, connectionTimeout, soTimeout, RedisConfig.DEFAULT_MAXATTE);
    }

    public RedisClusterConnPool(final Set<HostAndPort> clusterNodes,
                                final int connectionTimeout,
                                final int soTimeout,
                                final int maxAttempts) {
        this(clusterNodes, connectionTimeout, soTimeout, maxAttempts, new PoolConfig());
    }

    public RedisClusterConnPool(final Properties properties) {
        this(new PoolConfig(), properties);
    }

    public RedisClusterConnPool(final PoolConfig poolConfig, final Properties properties) {

        Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();

        for (String hostAndPort : properties.getProperty(RedisConfig.CLUSTER_PROPERTY).split(","))

            jedisClusterNodes.add(new HostAndPort(hostAndPort.split(":")[0], Integer.valueOf(hostAndPort.split(":")[1])));

        int timeout = Integer.parseInt(properties.getProperty(RedisConfig.TIMEOUT_PROPERTY, String.valueOf(RedisConfig.DEFAULT_TIMEOUT)));

        int maxAttempts = Integer.valueOf(properties.getProperty(RedisConfig.MAXATTE_PROPERTY, String.valueOf(RedisConfig.DEFAULT_MAXATTE)));

        String password = properties.getProperty(RedisConfig.PASSWORD_PROPERTY, RedisConfig.DEFAULT_PASSWORD);

        jedisCluster = new JedisCluster(jedisClusterNodes, timeout, timeout, maxAttempts, password, poolConfig);
    }

    public RedisClusterConnPool(final PoolConfig poolConfig, final Set<HostAndPort> clusterNodes) {
        this(clusterNodes, RedisConfig.DEFAULT_TIMEOUT, RedisConfig.DEFAULT_MAXATTE, poolConfig);
    }

    public RedisClusterConnPool(final Set<HostAndPort> clusterNodes,
                                final int timeout,
                                final int maxAttempts,
                                final PoolConfig poolConfig) {
        this(clusterNodes, timeout, timeout, maxAttempts, RedisConfig.DEFAULT_PASSWORD, poolConfig);
    }

    public RedisClusterConnPool(final Set<HostAndPort> clusterNodes,
                                final int connectionTimeout,
                                final int soTimeout,
                                final int maxAttempts,
                                final PoolConfig poolConfig) {
        this(clusterNodes, connectionTimeout, soTimeout, maxAttempts, RedisConfig.DEFAULT_PASSWORD, poolConfig);
    }



    @Override
    public JedisCluster getConnection() {

        return jedisCluster;
    }

    @Override
    public void returnConnection(JedisCluster conn) {
    }

    @Override
    public void invalidateConnection(JedisCluster conn) {
    }

    public void close() throws IOException {

        jedisCluster.close();
    }
}

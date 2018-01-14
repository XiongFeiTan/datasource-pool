package com.nemo.pool.hbase;

import com.nemo.pool.ConnectionPool;
import com.nemo.pool.PoolBase;
import com.nemo.pool.PoolConfig;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Connection;
import java.util.Properties;

public class HbaseConnectionPool extends PoolBase<Connection> implements ConnectionPool<Connection> {

	private static final long serialVersionUID = -8956854623145678989L;

	public HbaseConnectionPool(final PoolConfig poolConfig, final Configuration hadoopConfiguration) {
		super(poolConfig, new HbaseConnectionFactory(hadoopConfiguration));
	}

	public HbaseConnectionPool(final PoolConfig poolConfig, final String host, final String port, final String master, final String rootdir) {
		super(poolConfig, new HbaseConnectionFactory(host, port, master, rootdir));
	}

	public HbaseConnectionPool(final PoolConfig poolConfig, final Properties properties) {
		super(poolConfig, new HbaseConnectionFactory(properties));
	}
	@Override
	public Connection getConnection() {
		return super.getResource();
	}

	@Override
	public void returnConnection(Connection conn) {
		super.returnResource(conn);
	}

	@Override
	public void invalidateConnection(Connection conn) {
		super.invalidateResource(conn);
	}
	public HbaseConnectionPool() {
		this(HbaseConfig.DEFAULT_HOST, HbaseConfig.DEFAULT_PORT);
	}

	public HbaseConnectionPool(final String host, final String port) {
		this(new PoolConfig(), host, port, HbaseConfig.DEFAULT_MASTER, HbaseConfig.DEFAULT_ROOTDIR);
	}

	public HbaseConnectionPool(final String host, final String port, final String master, final String rootdir) {
		this(new PoolConfig(), host, port, master, rootdir);
	}

	public HbaseConnectionPool(final Configuration hadoopConfiguration) {
		this(new PoolConfig(), hadoopConfiguration);
	}

	public HbaseConnectionPool(final PoolConfig poolConfig, final String host, final String port) {
		this(poolConfig, host, port, HbaseConfig.DEFAULT_MASTER, HbaseConfig.DEFAULT_ROOTDIR);
	}


	


}

package com.nemo.pool.jdbc;

import com.nemo.pool.ConnectionPool;
import com.nemo.pool.PoolBase;
import com.nemo.pool.PoolConfig;
import java.sql.Connection;
import java.util.Properties;


public class JdbcConnectionPool
		extends PoolBase<Connection>
		implements ConnectionPool<Connection> {

	private static final long serialVersionUID = 7832163217894561238L;

	public JdbcConnectionPool(final PoolConfig poolConfig,
							  final Properties properties) {
		super(poolConfig, new JdbcConnectionFactory(properties));
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
}

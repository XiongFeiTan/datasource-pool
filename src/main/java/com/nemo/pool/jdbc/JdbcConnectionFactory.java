package com.nemo.pool.jdbc;

import com.nemo.pool.ConnectionFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

class JdbcConnectionFactory implements ConnectionFactory<Connection> {
	private static final long serialVersionUID = 4896534789654123568L;
	private final String driverClass;
	private final String jdbcUrl;
	private final String username;
	private final String password;
	private void LoadDriver() {
		try {
			Class.forName(driverClass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public JdbcConnectionFactory(final Properties properties) {
		this.driverClass = properties.getProperty(JdbcConfig.DRIVER_CLASS_PROPERTY);
		this.jdbcUrl = properties.getProperty(JdbcConfig.JDBC_URL_PROPERTY);
		this.username = properties.getProperty(JdbcConfig.JDBC_USERNAME_PROPERTY);
		this.password = properties.getProperty(JdbcConfig.JDBC_PASSWORD_PROPERTY);
		this.LoadDriver();
	}

	@Override
	public PooledObject<Connection> makeObject() throws Exception {

		Connection connection = this.createConnection();
		
		return new DefaultPooledObject<Connection>(connection);	
	}

	@Override
	public void destroyObject(PooledObject<Connection> p) throws Exception {
		
		Connection connection = p.getObject();

		if (connection != null)

			connection.close();
	}

	@Override
	public boolean validateObject(PooledObject<Connection> p) {

		Connection connection = p.getObject();
		
		if (connection != null)
			try {
				return ((!connection.isClosed()) && (connection.isValid(1)));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
		return false;
	}

	@Override
	public void activateObject(PooledObject<Connection> p) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void passivateObject(PooledObject<Connection> p) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public Connection createConnection() throws Exception {
		Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
		return connection;
	}

}

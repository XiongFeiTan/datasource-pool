package com.nemo.pool;

import java.io.Serializable;

public interface ConnectionPool<T> extends Serializable {
	//get connection
	 T getConnection();
	//return connection
	 void returnConnection(T conn);
	//invalidate connection
	 void invalidateConnection(T conn);
}

package com.nemo.pool;
import org.apache.commons.pool2.PooledObjectFactory;

import java.io.Serializable;
public interface ConnectionFactory<T> extends PooledObjectFactory<T>, Serializable {
	 T createConnection() throws Exception;
}

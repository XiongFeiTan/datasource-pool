package com.nemo.pool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import java.io.Serializable;

public class PoolConfig extends GenericObjectPoolConfig implements Serializable {
	private static final long serialVersionUID = -7893698521477896541L;
	public PoolConfig() {
		setTestWhileIdle(DEFAULT_TEST_WHILE_IDLE);
		setMinEvictableIdleTimeMillis(DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS);
		setTimeBetweenEvictionRunsMillis(DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS);
		setNumTestsPerEvictionRun(DEFAULT_NUM_TESTS_PER_EVICTION_RUN);
	}
	public static final boolean DEFAULT_TEST_WHILE_IDLE = true;
	public static final long  DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS = 60000;
	public static final long  DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS = 30000;
	public static final int  DEFAULT_NUM_TESTS_PER_EVICTION_RUN = -1;
}

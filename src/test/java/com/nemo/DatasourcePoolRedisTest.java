package com.nemo;
import com.nemo.pool.PoolConfig;
import com.nemo.pool.redis.RedisClusterConnPool;
import com.nemo.pool.redis.RedisConnectionPool;
import redis.clients.jedis.Jedis;
import java.util.List;
import java.util.Properties;

public class DatasourcePoolRedisTest {

	public static void main(String[] args) {
        // pool getConnection
        Jedis jedis = getConn();
        System.out.println(jedis);
        System.out.println("server running : "+jedis.ping());

        // save data into list
        jedis.lpush("test-list", "Nemo");
        jedis.lpush("test-list", "Google");

        // get the data ,and print
        List<String> list = jedis.lrange("test-list", 0 ,1);
        for(int i=0; i<list.size(); i++) {
            System.out.println("list : "+list.get(i));
        }
	}

    //static connection pool
    private static RedisConnectionPool pool;

    //init pool
    private static void initPool() {
        // poolConfig
        PoolConfig config = new PoolConfig();
        config.setMaxTotal(20);
        config.setMaxIdle(5);
        config.setMaxWaitMillis(1000);
        config.setMaxTotal(300);
        config.setMaxIdle(100);
        config.setMaxWaitMillis(10000);
        config.setTestOnBorrow(true);
        //  properties
        Properties props = new Properties();
        props.setProperty("address", "192.168.15.129:6379");
        props.setProperty("connectionTimeout", "2000");
        props.setProperty("soTimeout", "2000");
        props.setProperty("clientName", "nemo");
        //  connection pool
        pool = new RedisConnectionPool(config, props);
    }
    //get connection
    public synchronized static Jedis getConn() {
        if (pool == null){
            initPool();
        }
        return pool.getConnection();
    }
    //relase connection
    public static void releaseConn(final Jedis jedis){
        synchronized (DatasourcePoolRedisTest.class) {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

}

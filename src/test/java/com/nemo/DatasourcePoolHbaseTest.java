package com.nemo;

import com.nemo.pool.PoolConfig;
import com.nemo.pool.hbase.HbaseConnectionPool;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Properties;

public class DatasourcePoolHbaseTest {
    private static Logger LOG = LoggerFactory.getLogger(DatasourcePoolHbaseTest.class);

    public static void main(String[] args)throws Exception {
        PoolConfig config = new PoolConfig();
        config.setMaxTotal(20);
        config.setMaxIdle(5);
        config.setMaxWaitMillis(10000);
        config.setTestOnBorrow(true);
        Properties props = new Properties();
        props.setProperty("hbase.zookeeper.quorum", "localhost");
        props.setProperty("hbase.zookeeper.property.clientPort", "2181");
        // connection pool
        HbaseConnectionPool pool = new HbaseConnectionPool(config, props);
        Table table = null;
        Connection conn = pool.getConnection();
        table = conn.getTable(TableName.valueOf("nemo"));
        //insert one data key:key-nemo,family:f,qualifier:q,value:1L
        Put put = new Put(Bytes.toBytes("key-nemo"));
        put.addColumn(Bytes.toBytes("f"), Bytes.toBytes("q"), Bytes.toBytes(1L));
        table.put(put);
        Result s = table.get(new Get(Bytes.toBytes("key-nemo")));
        byte[] bytes = s.getValue(Bytes.toBytes("f"), Bytes.toBytes("q"));
        System.out.println(Bytes.toLong(bytes));
        //close table,return connection,close pool
        table.close();
        pool.returnConnection(conn);
        pool.close();
    }

}

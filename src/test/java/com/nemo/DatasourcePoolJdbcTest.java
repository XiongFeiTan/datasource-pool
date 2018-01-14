package com.nemo;

import com.nemo.pool.PoolConfig;
import com.nemo.pool.jdbc.JdbcConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;
import java.util.Properties;

/**
 * @author Nemo
 * @version V1.0  2017-12-29
 **/
public class DatasourcePoolJdbcTest {
    private static Logger LOG = LoggerFactory.getLogger(DatasourcePoolJdbcTest.class);


    public static void main(String[] args){
        String url = "jdbc:mysql://localhost:3306/test";
        String username = "root";
        String password = "123456";
        Connection conn = null;
        PreparedStatement ps = null;
        StringBuffer sqlSb = new StringBuffer(256);
        sqlSb.append(" select username from user ");
        String sql = sqlSb.toString();
        ResultSet rs = null;
        try {
            conn = getConn(url, username, password);
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()) {
                System.out.println(rs.getString(1));
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            releaseConn(rs, ps, conn);
        }
    }

    //static connection pool
    private static JdbcConnectionPool pool;

    //init pool
    private static void initPool(String url, String userName, String passWord) {
        PoolConfig config = new PoolConfig();
        config.setMaxTotal(1024);
        config.setMaxIdle(5);
        config.setMaxWaitMillis(1000);
        config.setTestOnBorrow(true);

        Properties props = new Properties();
        props.setProperty("driverClass", "com.mysql.jdbc.Driver");
        props.setProperty("jdbcUrl", url);
        props.setProperty("username", userName);
        props.setProperty("password", passWord);

        pool = new JdbcConnectionPool(config, props);
    }

    //get connection
    public synchronized static Connection getConn(String url, String userName, String passWord) {
        if (pool == null){
            initPool(url, userName, passWord);
        }
        return pool.getConnection();
    }

    //release connection
    public static void releaseConn(ResultSet rs, Statement stat, Connection conn) {
        synchronized (DatasourcePoolJdbcTest.class) {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    LOG.error("close jdbc ResultSet fail!");
                }
            }
            if (stat != null) {
                try {
                    stat.close();
                } catch (SQLException e) {
                    LOG.error("close jdbc Statement fail!");
                }
            }
            if (conn != null && pool != null) {
                pool.returnConnection(conn);
            }
        }
    }

}

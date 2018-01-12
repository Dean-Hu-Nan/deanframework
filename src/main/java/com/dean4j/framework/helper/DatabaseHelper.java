package com.dean4j.framework.helper;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * JDBC 帮助类
 *
 * @author hunan
 * @since 1.0.0
 */
public final class DatabaseHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHelper.class);

    private static final String DRIVER;
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;

    /**
     * 定义一个局部线程变量（使每个线程都拥有自己的连接）
     */
    private static final ThreadLocal<Connection> CONN_CONTAINER = new ThreadLocal<Connection>();

    /**
     * 数据库连接池
     */
    private static final BasicDataSource SOURCE = new BasicDataSource();


    static {
        DRIVER = ConfigHelper.getJdbcDriver();
        URL = ConfigHelper.getJdbcUrl();
        USERNAME = ConfigHelper.getJdbcUsername();
        PASSWORD = ConfigHelper.getJdbcPassword();

        SOURCE.setDriverClassName(DRIVER);
        SOURCE.setUrl(URL);
        SOURCE.setUsername(USERNAME);
        SOURCE.setPassword(PASSWORD);
    }

    /**
     * 获取数据库连接
     */
    public static Connection getConnection() {
        Connection conn;
        try {
            // 先从 ThreadLocal 中获取 Connection
            conn = CONN_CONTAINER.get();
            if (conn == null) {
                // 若不存在，则从池中获取 Connection
                conn = SOURCE.getConnection();
                // 将 Connection 放入 ThreadLocal 中
                if (conn != null) {
                    CONN_CONTAINER.set(conn);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("获取数据库连接出错！", e);
            throw new RuntimeException(e);
        }
        return conn;
    }

    /**
     * 开启事务
     */
    public static void beginTransaction() {
        Connection conn = getConnection();
        if (conn != null) {
            try {
                conn.setAutoCommit(false);
            } catch (SQLException e) {
                LOGGER.error("开启事务出错！", e);
                throw new RuntimeException(e);
            } finally {
                CONN_CONTAINER.set(conn);
            }
        }
    }

    /**
     * 提交事务
     */
    public static void commitTransaction() {
        Connection conn = getConnection();
        if (conn != null) {
            try {
                conn.commit();
                conn.close();
            } catch (SQLException e) {
                LOGGER.error("提交事务出错！", e);
                throw new RuntimeException(e);
            } finally {
                CONN_CONTAINER.remove();
            }
        }
    }

    /**
     * 回滚事务
     */
    public static void rollbackTransaction() {
        Connection conn = getConnection();
        if (conn != null) {
            try {
                conn.rollback();
                conn.close();
            } catch (SQLException e) {
                LOGGER.error("回滚事务出错！", e);
                throw new RuntimeException(e);
            } finally {
                CONN_CONTAINER.remove();
            }
        }
    }
}

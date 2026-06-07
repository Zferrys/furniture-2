package com.furniture.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


/**
 * @author zph
 * @version 1.0
 * 基于 Druid数据库连接池的工具类
 */
public class JDBCUtilsByDruid {

    private static DataSource ds;
    private static ThreadLocal<Connection> threadLocalConn = new ThreadLocal<>();

    //静态代码块初始化
    static {
        Properties properties = new Properties();
        try {
            properties.load(JDBCUtilsByDruid.class.getClassLoader().getResourceAsStream("druid.properties"));
            ds = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() throws SQLException {

        Connection connection = threadLocalConn.get();
        if (connection == null) {
            try {
                connection = ds.getConnection();
                connection.setAutoCommit(false);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            threadLocalConn.set(connection);
        }
        return connection;
    }

    public static void commit(){
        Connection connection = threadLocalConn.get();
        if (connection != null) {
            try {
                connection.commit();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            threadLocalConn.remove();
        }
    }

    public static void rollback(){
        Connection connection = threadLocalConn.get();
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            threadLocalConn.remove();
        }
    }

    /**
     * 清理ThreadLocal中的连接
     */
    public static void clearConnection(){
        threadLocalConn.remove();
    }


    public static void close(ResultSet resultSet, Statement statement, Connection connection) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

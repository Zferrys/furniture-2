package com.furniture.dao;





import com.furniture.utils.JDBCUtilsByDruid;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author zph
 * @version 1.0
 * 开发BasicDAO ,是其他DAO的父类
 */
public class BasicDAO<T> {

    private QueryRunner qr = new QueryRunner();

    //开发通用的dml方法，针对任意表
    public int update(String sql, Object... parameters) {
        Connection connection = null;
        try {
            connection = JDBCUtilsByDruid.getConnection();
            int update = qr.update(connection, sql, parameters);
            return update;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //返回多个对象(即查询的结果是多行),针对任意表

    /**
     * @param sql        就是sql语句
     * @param clazz      传入一个类的Class对象,比如 Admin.class
     * @param parameters 传入占位符参数,可以多个
     * @return 根据 Admin.class 返回对应的Arraylist 集合
     */
    public List<T> queryMulti(String sql, Class<T> clazz, Object... parameters) {
        Connection connection = null;
        try {
            connection = JDBCUtilsByDruid.getConnection();
            return qr.query(connection, sql, new BeanListHandler<T>(clazz), parameters);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //查询单行结果的通用方法
    public T querySingle(String sql, Class<T> clazz, Object... parameters) {
        Connection connection = null;
        try {
            connection = JDBCUtilsByDruid.getConnection();
            return qr.query(connection, sql, new BeanHandler<T>(clazz), parameters);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //查询单行单列方法（即单值）
    public Object queryScalar(String sql,Object... parameters){
        Connection connection = null;
        try {
            connection = JDBCUtilsByDruid.getConnection();
            return qr.query(connection, sql, new ScalarHandler(), parameters);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

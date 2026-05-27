package com.furniture.test;



import com.furniture.utils.JDBCUtilsByDruid;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author zph
 * @version 1.0
 */
public class JdbcUtils {
    public static void main(String[] args) throws SQLException {
        Connection connection = JDBCUtilsByDruid.getConnection();
        System.out.println(connection);
        JDBCUtilsByDruid.close(null,null,connection);
    }
}

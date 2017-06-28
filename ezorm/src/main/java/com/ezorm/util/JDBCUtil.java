package com.ezorm.util;


import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by Li Yu on 2017/6/9.
 */
public class JDBCUtil {
    public static Connection getConnection(){
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/count?characterEncoding=utf8&useSSL=true";
        String username = "root";
        String password = "1028";
        Connection conn = null;
        try{
            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
        }catch (Exception e){
            e.printStackTrace();
        }

        return conn;
    }

}

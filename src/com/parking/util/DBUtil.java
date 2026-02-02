package com.parking.util;
import java.sql.*;
public class DBUtil{
    public static Connection getDBConnection(){
        Connection conn = null;
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String url = "jdbc:oracle:thin:@localhost:1522:xe"; 
            conn = DriverManager.getConnection(url, "system", "System123");
        }catch(Exception e){
            e.printStackTrace();
        }
        return conn;
    }
}
package ims;
import java.sql.*;
import java.util.ArrayList;

public class SqlController {
    //Database connection objects
    protected static Connection dbConnection;
    protected static Statement dbStatement;
    public static void initializeSql(String dbUserName, String dbPassword, String dbIpAddress, String dbPort, String dbName) throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        dbConnection = DriverManager.getConnection("jdbc:mysql://" + dbIpAddress + ":" + dbPort + "/" + dbName, dbUserName, dbPassword);
        dbStatement = SqlController.dbConnection.createStatement();
    }
}

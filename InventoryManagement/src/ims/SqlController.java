package ims;
import java.sql.*;

public class SqlController {

    //Database connection information
    private static String dbUserName = "casiano"; //Username for MySQL with rights to database
    private static String dbPassword = "password"; //Password for MySQL user with rights to database
    private static String dbIpAddress = "10.10.100.4"; //IP address the database is hosted at (probably localhost)
    private static String dbPort = "3306"; //Port of the database (probably 3306)
    private static String dbName = "inventory_management"; //Name of the database to modify

    //Database connection objects
    protected static Connection dbConnection;
    protected static Statement dbStatement;
    public static void initializeSql() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        dbConnection = DriverManager.getConnection("jdbc:mysql://" + dbIpAddress + ":" + dbPort + "/" + dbName,dbUserName,dbPassword);
        dbStatement = SqlController.dbConnection.createStatement();
    }

    //testing function
    public static void main(String[] args){

    }
}

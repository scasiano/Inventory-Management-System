package ims;
import java.sql.*;
import java.util.ArrayList;

public class SqlController {

    //Database connection information
    private static String dbUserName = "jayspy"; //Username for MySQL with rights to database
    private static String dbPassword = "password"; //Password for MySQL user with rights to database
    private static String dbIpAddress = "192.168.40.129"; //IP address the database is hosted at (probably localhost)
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
       /* try{
        initializeSql();
        ArrayList<String> prodName =new ArrayList<String>();
        prodName=Products.selectName();
        for(int i=0;i<prodName.size();i++)
        {
            System.out.println(prodName.get(i));
        }
        }catch(Exception e)
        {e.printStackTrace();}*/
    }
}

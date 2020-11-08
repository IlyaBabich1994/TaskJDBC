package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static String DB_HOST  = "localhost";
    private static String DB_USERNAME = "root";
    private static String DB_PASSWORD = "00000000";
    private static String DB_NAME = "Users";
    private static String DB_PORT = ":3306/";
    private static String URL = "jdbc:mysql://"
            + DB_HOST
            + DB_PORT
            + DB_NAME
            + "?serverTimezone=Europe/Moscow&useSSL=false";

    public static Connection getMySQLConnection(String connectionURL,
                                          String username,
                                          String password)
            throws SQLException, ClassNotFoundException {

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection =  DriverManager.getConnection(connectionURL,
                username, password);
        return connection;
    }

    public static Connection getMySQLConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return getMySQLConnection(URL, DB_USERNAME, DB_PASSWORD);
    }
}

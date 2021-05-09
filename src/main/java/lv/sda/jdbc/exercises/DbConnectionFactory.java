package lv.sda.jdbc.exercises;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnectionFactory {

    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static final String DB_NAME = "examples";
    private static final String DB_URI = "jdbc:mysql://localhost:3306/" + DB_NAME
            + "?autoReconnect=true&useSSL=false&characterEncoding=utf8";

    public static Connection openConnection() {
        try {
            Connection connection = DriverManager.getConnection(DB_URI, USERNAME, PASSWORD);
            connection.setAutoCommit(true);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

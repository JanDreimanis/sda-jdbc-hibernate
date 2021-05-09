package lv.sda.jdbc.examples.teacher;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeacherRepository {

    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static final String DB_NAME = "examples";
    private static final String DB_URI = "jdbc:mysql://localhost:3306/" + DB_NAME
            + "?autoReconnect=true&useSSL=false&characterEncoding=utf8";

    private Connection connection;

    TeacherRepository() {
        initDatabaseConnection();
    }

    private void initDatabaseConnection() {
        /*
		   When new TeacherRepository is created, create connection to the database server:
         	Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URI, USERNAME, PASSWORD);
            conn.setAutoCommit(false); //Use conn.commit() after each Insert/Update/Delete call
		   */
        try {
            connection = DriverManager.getConnection(DB_URI, USERNAME, PASSWORD);
            connection.setAutoCommit(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * Returns a Teacher instance represented by the specified ID.
     */
    Teacher findBy(int id) {
        /*
         Execute an SQL statement that searches teacher by ID.
         If teacher is found return Teacher object with values from DB
         If teacher is not found return null
         */
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT id, first_name, " +
                    "last_name FROM teacher WHERE id = ?");
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return new Teacher(rs.getInt(1), rs.getString(2), rs.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns a list of Teacher objects.
     */
    public List<Teacher> findBy(String firstName, String lastName) {
        /*
           Write an sql statement that searches teacher by first and last name and returns results as ArrayList<Teacher>.
           Result list should include all partial results as well, e.g. if first name is matching but last name is not
           still include, the teacher in result list, same applies for lastName
           If nothing is found return empty list!
        */
        List<Teacher> result = new ArrayList<>();
        try {
            PreparedStatement pStmt =
                    connection.prepareStatement(
                            "SELECT * FROM teacher WHERE first_name like ? or last_name like ?");
            pStmt.setString(1, firstName);
            pStmt.setString(2, lastName);
            ResultSet rs = pStmt.executeQuery();
            if (rs.next()) {
                result.add(new Teacher(rs.getInt(1), rs.getString(2), rs.getString(3)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Insert an new teacher (first name and last name) into the table.
     */
    public boolean insert(String firstName, String lastName) {
        /*
           Execute an SQL statement that inserts teacher in database.
           SQL statement should contain only firstName and lastName, ID should be automatically generated by DB
         */
        try {
            PreparedStatement pStmt = connection
                    .prepareStatement("INSERT INTO teacher (first_name, last_name) VALUES (?, ?)");
            pStmt.setString(1, firstName);
            pStmt.setString(2, lastName);
            pStmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * Insert teacher object into database
     */
    public boolean insert(Teacher teacher) {
        /*
        Execute an SQL statement that inserts teacher in database.
        SQL statement should contain all fields: id, firstName and lastName
        If teacher is inserted succesfully return true, otherwise false
         */
        try {
            PreparedStatement pStmt = connection
                    .prepareStatement("INSERT INTO teacher (id, first_name, last_name) VALUES (?, ?, ?)");
            pStmt.setInt(1, teacher.getId());
            pStmt.setString(2, teacher.getFirstName());
            pStmt.setString(3, teacher.getLastName());
            int result = pStmt.executeUpdate();
            return result == 1 ? true : false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Updates an existing Teacher in the repository with the values represented by the Teacher object.
     */
    public boolean update(Teacher teacher) {
        /*
            Execute an SQL statement that updates teacher information.
            Update teacher in database by it's ID
            If ONE teacher is successfully updated, return true, otherwise false
         */
        try {
            PreparedStatement pStmt = connection
                    .prepareStatement("UPDATE teacher SET first_name = ?, last_name = ? where ID = ?");
            pStmt.setString(1, teacher.getFirstName());
            pStmt.setString(2, teacher.getLastName());
            pStmt.setInt(3, teacher.getId());
            int result = pStmt.executeUpdate();
            return result == 1 ? true : false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        /*
            Execute an SQL statement that deletes teacher from database.
            Delete teacher by it's ID
            If one teacher is successfully deleted, return true
            If no teacher is deleted return false
         */
        try {
            PreparedStatement pStmt = connection.prepareStatement("DELETE FROM teacher WHERE ID = ?");
            pStmt.setInt(1, id);
            int rows = pStmt.executeUpdate();
            pStmt.close();
            return rows == 1 ? true : false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void closeConnection() {
        /*
            Close connection to the database server and reset conn object to null
         */
        try {
            if (connection != null) {
                connection.close();
            }
            connection = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

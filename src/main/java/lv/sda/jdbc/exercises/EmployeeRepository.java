package lv.sda.jdbc.exercises;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static java.util.Collections.emptyList;
import static lv.sda.jdbc.exercises.EmployeesSql.INSERT_INTO_EMPLOYEES_QUERY;
import static lv.sda.jdbc.exercises.EmployeesSql.SELECT_EMPLOYEE_BY_DEPARTMENT_QUERY;
import static lv.sda.jdbc.exercises.EmployeesSql.SELECT_EMPLOYEE_BY_ID_QUERY;

public class EmployeeRepository {

    private final EmployeeMapper employeeMapper = new EmployeeMapper();

    private Connection connection;

    EmployeeRepository() {
        connection = DbConnectionFactory.openConnection();
    }

    Employee findById(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_EMPLOYEE_BY_ID_QUERY);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return employeeMapper.mapToEmployee(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    boolean insert(Employee employee) {
        try {
            PreparedStatement statement = connection.prepareStatement(INSERT_INTO_EMPLOYEES_QUERY);
            statement.setInt(1, employee.getId());
            statement.setString(2, employee.getFirstName());
            statement.setString(3, employee.getLastName());
            statement.setInt(4, employee.getDepartmentId());
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    List<Employee> findByDepartment(String departmentName) {
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_EMPLOYEE_BY_DEPARTMENT_QUERY);
            statement.setString(1, departmentName);
            return employeeMapper.toList(statement.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emptyList();
    }

    void closeConnection() {
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

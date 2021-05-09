package lv.sda.jdbc.exercises;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class EmployeeMapper {

    public List<Employee> toList(ResultSet result) throws SQLException {
        List<Employee> employees = new ArrayList<>();
        while (result.next()) {
            employees.add(mapToEmployee(result));
        }
        return employees;
    }

    public Employee mapToEmployee(ResultSet resultSet) throws SQLException {
        Employee employee = new Employee();
        employee.setId(resultSet.getInt("id"));
        employee.setFirstName(resultSet.getString("first_name"));
        employee.setLastName(resultSet.getString("last_name"));
        employee.setDepartmentId(resultSet.getInt("department_id"));
        return employee;
    }
}

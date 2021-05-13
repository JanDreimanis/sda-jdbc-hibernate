package lv.sda.jdbc.exercises;

final class EmployeesSql {

    private static final String SELECT_FROM_EMPLOYEES = "SELECT employees.id, first_name, " +
            "last_name, department_id FROM employees ";

    static final String SELECT_EMPLOYEE_BY_ID_QUERY = SELECT_FROM_EMPLOYEES + "WHERE id = ?";
    static final String SELECT_EMPLOYEE_BY_DEPARTMENT_QUERY = SELECT_FROM_EMPLOYEES +
            "inner join departments " +
            "on departments.id = employees.department_id " +
            "where departments.department like ?";

    static final String INSERT_INTO_EMPLOYEES_QUERY = "INSERT INTO employees VALUES (?, ?, ?, ?)";

    private EmployeesSql() {
    }
}

package lv.sda.jdbc.exercises;

public class ExampleMain {

    public static void main(String[] args) {
        Employee employee = new Employee();
        employee.setFirstName("Janis");
        change(employee);
        System.out.println(employee.getFirstName());

        int x = 10;
        change(x);
        System.out.println(x);
    }

    public static void change(Employee param) {
        param.setFirstName("Juris");
    }

    public static void change(int number) {
        number = 5;
    }
}

package lv.sda.jdbc.examples.teacher;

public class JdbcExample {

    public static void main(String[] args) {
        TeacherRepository repository = new TeacherRepository();

        System.out.println(repository.findBy(1));

        Teacher teacher = repository.findBy(56);
        if (teacher == null) {
            System.out.println("Teacher for update not found.");
            repository.closeConnection();
            return;
        }
        teacher.setFirstName("Janis");
        teacher.setLastName("Logins");

        boolean isUpdated = repository.update(teacher);

        if (isUpdated) {
            System.out.println("Updated teacher!");
        } else {
            System.out.println("update failed");
        }

        repository.closeConnection();
    }
}

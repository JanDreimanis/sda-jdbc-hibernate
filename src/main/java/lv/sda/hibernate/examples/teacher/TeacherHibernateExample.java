package lv.sda.hibernate.examples.teacher;

import java.util.List;
import java.util.Random;

//CRUD examples
public class TeacherHibernateExample {

    static final String SURNAME_PREFIX = "random";

    public static void main(String[] args) {
        TeacherRepository repository = new TeacherRepository();

        String randomLastName = generateRandomString();
        Teacher teacher = repository.insert(new Teacher("John", randomLastName));
        System.out.println("new teacher: " + teacher);


        List<Teacher> teachers = repository.findAll();
        teachers.forEach(System.out::println);


        List<Teacher> teacherFoundByName = repository.findBy(teacher.getFirstName(), SURNAME_PREFIX);
        System.out.println("found by name / surname: " + teacherFoundByName.get(0));


        teacher.setLastName("Doe - updated!");
        repository.update(teacher);
        System.out.println("updated teacher:" + repository.findBy(teacher.getId()));


        int deletedRowsCount = repository.delete(teacher.getId());
        if (deletedRowsCount == 1) {
            System.out.println("delete teacher with ID : " + teacher.getId());
        } else {
            System.out.println("Delete failed");
        }

        HibernateUtils.close();
    }

    static String generateRandomString() {
        return SURNAME_PREFIX + " " + new Random().nextInt();
    }
}

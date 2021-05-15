package lv.sda.hibernate.examples.teacher;

import org.hibernate.query.Query;

import java.util.List;

import static lv.sda.hibernate.examples.teacher.HibernateUtils.runInTransaction;

class TeacherRepository {

    //example with HQL (hibernate query language)
    List<Teacher> findAll() {
        return runInTransaction(session -> {
            return session.createQuery("from Teacher", Teacher.class).list();
        });
    }

    Teacher findBy(int id) {
       return runInTransaction(session -> {
           return session.find(Teacher.class, id);
       });
    }

    List<Teacher> findBy(String firstName, String lastName) {
        return runInTransaction(session -> {
            Query<Teacher> query = session.createQuery("from Teacher t where t.firstName like :firstName " +
                        "and t.lastName like :lastName", Teacher.class);

            //set parameters
            query.setParameter("firstName", firstName + "%")
                    .setParameter("lastName", lastName + "%");

            //run the query
            return query.list();
        });
    }

    Teacher insert(String firstName, String lastName) {
        return insert(new Teacher(firstName, lastName));
    }

    Teacher insert(Teacher teacher) {
         return runInTransaction(session -> {
            session.save(teacher);
            return teacher;
        });
    }

    Teacher update(Teacher teacher) {
        return runInTransaction(session -> {
            session.update(teacher);
            return teacher;
        });
    }

    int delete(int id) {
        return runInTransaction(session -> {
            return session.createQuery("delete from Teacher t where t.id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
        });
    }
}

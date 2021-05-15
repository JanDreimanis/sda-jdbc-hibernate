package lv.sda.hibernate.examples.teacher;

import org.hibernate.Session;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;
import java.util.function.Consumer;
import java.util.function.Function;

public class HibernateUtils {

    private static SessionFactory sessionFactory;

    public static Session currentSession() {
        return getSessionFactory().getCurrentSession();
    }

    public static SessionFactory getSessionFactory() {
        //this is not thread safe
        if (sessionFactory != null) {
            return sessionFactory;
        }
        try {
            Configuration configuration = createConfiguration();
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            return sessionFactory;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("unable to create session factory", e);
        }
    }

    public static void runInTransaction(Consumer<Session> consumer) {
        runInTransaction(session -> {
            consumer.accept(session);
            return null;
        });
    }

    public static <T> T runInTransaction(Function<Session, T> consumer) {
        Transaction transaction = null;
        try {
            Session session = currentSession();
            transaction = session.beginTransaction();

            T result = consumer.apply(session);

            transaction.commit();
            return result;
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(ex);
        }
    }

    // Close caches and connection pools
    public static void close() {
        getSessionFactory().close();
    }

    private static Configuration createConfiguration() {
        Configuration configuration = new Configuration();
        configuration.setProperties(createProperties());
        configuration.addAnnotatedClass(Teacher.class);
        return configuration;
    }

    private static Properties createProperties() {
        Properties properties = new Properties();
        properties.put(Environment.DRIVER, "com.mysql.jdbc.Driver");
        properties.put(Environment.URL, "jdbc:mysql://localhost:3306/examples?serverTimezone=UTC&useSSL=false");
        properties.put(Environment.USER, "root");
        properties.put(Environment.PASS, "");
        properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
        properties.put(Environment.SHOW_SQL, "false");
        properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        properties.put(Environment.HBM2DDL_AUTO, "validate");
        return properties;
    }
}

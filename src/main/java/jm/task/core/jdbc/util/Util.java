package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    // реализуйте настройку соеденения с БД

    private static final Util INSTANCE = new Util();
    private static Connection connection;
    private static SessionFactory sessionFactory;
    private static final String DB_URL = "jdbc:postgresql://localhost:5433/postgres";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "root";

    private Util() {
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
        return connection;
    }

    public static Util getInstance() {
        return INSTANCE;
    }

    public static synchronized SessionFactory getSessionFactory() {
        if (sessionFactory == null || sessionFactory.isClosed()) {
            try {
                Properties properties = new Properties();
                properties.put(Environment.DRIVER, "org.postgresql.Driver");
                properties.put(Environment.URL, DB_URL);
                properties.put(Environment.USER, USERNAME);
                properties.put(Environment.PASS, PASSWORD);
                properties.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");
                properties.put(Environment.HBM2DDL_AUTO, "update");
                properties.put(Environment.SHOW_SQL, true);
                properties.put(Environment.FORMAT_SQL, true);
                properties.put(Environment.USE_SQL_COMMENTS, true);

                Configuration configuration = new Configuration();
                configuration.setProperties(properties);
                configuration.addAnnotatedClass(User.class);

                sessionFactory = configuration
                        .buildSessionFactory(new StandardServiceRegistryBuilder()
                                .applySettings(configuration.getProperties())
                                .build());
            } catch (Exception e) {
                System.err.println("SessionFactory creation failed: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}

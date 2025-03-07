package jm.task.core.jdbc.util;
import jm.task.core.jdbc.model.User;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    // реализуйте настройку соеденения с БД
    private static volatile Util instance = null;
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/pp114";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    private Util() {}

    public static Util getInstance() {
        if (instance == null) {
            synchronized (Util.class) {
                if (instance == null) {
                    instance = new Util();
                }
            }
        }
        return instance;
    }

    static SessionFactory sessionFactory = null; //Ментор оставил замечание "Вынести SessionFactory в поле класса дао"

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                Properties properties = new Properties();
                properties.put(Environment.DRIVER, JDBC_DRIVER);
                properties.put(Environment.URL, DB_URL);
                properties.put(Environment.USER, DB_USER);
                properties.put(Environment.PASS, DB_PASSWORD);
                properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");

                properties.put(Environment.SHOW_SQL, "true");

                properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

                properties.put(Environment.HBM2DDL_AUTO, "create-drop");

                configuration.setProperties(properties);

                configuration.addAnnotatedClass(User.class);
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties())
                        .build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);


            } catch (Exception e) {
                System.out.println("Исключение!" + e.getMessage());
            }
        }
        return sessionFactory;
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Connected to database");
        } catch (SQLException e) {
            System.out.println("Connection failed, SQLException " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Driver problem, ClassNotFoundException " + e.getMessage());
        }
        return connection;
    }
}
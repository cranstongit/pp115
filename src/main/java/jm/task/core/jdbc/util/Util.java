package jm.task.core.jdbc.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
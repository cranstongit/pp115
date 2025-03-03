package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class UserDaoJDBCImpl implements UserDao {
    Util util = Util.getInstance();

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        Connection connection = null;
        String createTableSQL = "CREATE TABLE IF NOT EXISTS bbusers (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY," +
                        "name VARCHAR(50) NOT NULL," +
                        "lastname VARCHAR(50) NOT NULL," +
                        "age INT NOT NULL" +
                        ")";

        try {
             connection = util.getConnection();
             connection.setAutoCommit(false);
             try (Statement statement = connection.createStatement()) {
                 statement.execute(createTableSQL);
                 connection.commit();
                 System.out.println("Таблица пользователей создана успешно.");
             } catch (SQLException e) {
                 connection.rollback();
                 System.out.println("Проблема с созданием таблицы пользователей " + e.getMessage());
             }
        } catch (SQLException e) {
             System.out.println("Ошибка при получении соединения " + e.getMessage());
        } finally {
             try {
                 if (connection != null) {
                     connection.close();
                 }
             } catch (SQLException e) {
                 System.out.println("Ошибка при закрытии соединения " + e.getMessage());
             }
        }
    }

    public void dropUsersTable() {
        Connection connection = null;
        String dropTableSQL = "DROP TABLE IF EXISTS bbusers";

        try {
            connection = util.getConnection();
            connection.setAutoCommit(false);
            try (Statement statement = connection.createStatement()) {
                statement.execute(dropTableSQL);
                connection.commit();
                System.out.println("Таблица пользователей удалена успешно.");
            } catch (SQLException e) {
                connection.rollback();
                System.out.println("Проблема с удалением таблицы пользователей " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при получении соединения " + e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println("Ошибка при закрытии соединения " + e.getMessage());
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        Connection connection = null;
        String insertUserSQL = "INSERT INTO bbusers (name, lastname, age) VALUES (?, ?, ?)";

        try {
            connection = util.getConnection();
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertUserSQL)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, lastName);
                preparedStatement.setByte(3, age);
                preparedStatement.executeUpdate();
                connection.commit();
                System.out.println("Пользователь " + name + " " + lastName + " " + age + " успешно добавлен в таблицу.");
            } catch (SQLException e) {
                connection.rollback();
                System.out.println("Проблема с добавлением пользователей " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при получении соединения " + e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println("Ошибка при закрытии соединения " + e.getMessage());
            }
        }
    }

    public void removeUserById(long id) {
        Connection connection = null;
        String deleteUserSQL = "DELETE FROM bbusers WHERE id = ?";

        try {
            connection = util.getConnection();
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteUserSQL)) {
                preparedStatement.setLong(1, id);
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    connection.commit();
                    System.out.println("Пользователь с id " + id + " успешно удалён.");
                } else {
                    connection.rollback();
                    System.out.println("Пользователь с id " + id + " не найден.");
                }
            } catch (SQLException e) {
                connection.rollback();
                System.out.println("Проблема с удалением пользователя по id " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при получении соединения " + e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println("Ошибка при закрытии соединения " + e.getMessage());
            }
        }
    }

    public List<User> getAllUsers() {
        List<User> bbusers = new ArrayList<>();
        Connection connection = null;
        String getAllbbusersSQL = "SELECT * FROM bbusers";

        try {
            connection = util.getConnection();
            connection.setAutoCommit(false);
            try (Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(getAllbbusersSQL)) {

                while (resultSet.next()) {
                    long id = resultSet.getLong("id");
                    String name = resultSet.getString("name");
                    String lastName = resultSet.getString("lastname");
                    byte age = resultSet.getByte("age");

                    User user = new User(name, lastName, age);
                    user.setId(id);
                    bbusers.add(user);
                }
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                System.out.println("Проблема с добавлением пользователя в список " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при получении соединения " + e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println("Ошибка при закрытии соединения " + e.getMessage());
            }
        }
        System.out.println("Сохранен список пользователей таблицы");
        return bbusers;
    }

    public void cleanUsersTable() {
        Connection connection = null;
        String deleteSQL = "DELETE FROM bbusers";

        try {
            connection = util.getConnection();
            connection.setAutoCommit(false);
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(deleteSQL);
                connection.commit();
                System.out.println("Таблица пользователей очищена успешно.");
            } catch (SQLException e) {
                connection.rollback();
                System.out.println("Проблема с очищением таблицы пользователей " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при получении соединения " + e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println("Ошибка при закрытии соединения " + e.getMessage());
            }
        }
    }

    public void changeAgeById(long id, byte age) {
        Connection connection = null;
        String updateAgeSQL = "UPDATE bbusers SET age = ? WHERE id = ?";

        try {
            connection = util.getConnection();
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateAgeSQL)) {
                preparedStatement.setByte(1, age);
                preparedStatement.setLong(2, id);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    connection.commit();
                    System.out.println("Возраст пользователя с id " + id + " успешно обновлён.");
                } else {
                    connection.rollback();
                    System.out.println("Пользователь с id " + id + " не найден.");
                }
            } catch (SQLException e) {
                connection.rollback();
                System.out.println("Проблема с изменением возраста пользователя " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при получении соединения " + e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println("Ошибка при закрытии соединения " + e.getMessage());
            }
        }
    }
}

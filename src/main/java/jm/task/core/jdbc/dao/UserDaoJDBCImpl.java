package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    Util util = Util.getInstance();
    Connection connection = util.getConnection();

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
         String createTableSQL = "CREATE TABLE IF NOT EXISTS bbusers (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY," +
                        "name VARCHAR(50) NOT NULL," +
                        "lastname VARCHAR(50) NOT NULL," +
                        "age INT NOT NULL" +
                        ")";

         try (Statement statement = connection.createStatement()) {
             statement.execute(createTableSQL);
             System.out.println("Таблица пользователей создана успешно.");
         } catch (SQLException e) {
             System.out.println("Проблема с созданием таблицы пользователей " + e.getMessage());
         }
    }

    public void dropUsersTable() {
        String dropTableSQL = "DROP TABLE IF EXISTS bbusers";

        try (Statement statement = connection.createStatement()) {
            statement.execute(dropTableSQL);
            System.out.println("Таблица пользователей удалена успешно.");
        } catch (SQLException e) {
            System.out.println("Проблема с удалением таблицы пользователей " + e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String insertUserSQL = "INSERT INTO bbusers (name, lastname, age) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertUserSQL)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("Пользователь " + name + " " + lastName + " " + age + " успешно добавлен в таблицу.");
        } catch (SQLException e) {
            System.out.println("Проблема с добавлением пользователей " + e.getMessage());
        }
    }

    public void removeUserById(long id) {
        String deleteUserSQL = "DELETE FROM bbusers WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteUserSQL)) {
            preparedStatement.setLong(1, id);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Пользователь с id " + id + " успешно удалён.");
            } else {
                System.out.println("Пользователь с id " + id + " не найден.");
            }
        } catch (SQLException e) {
            System.out.println("Проблема с удалением пользователя по id " + e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        List<User> bbusers = new ArrayList<>();
        String getAllbbusersSQL = "SELECT * FROM bbusers";

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
        } catch (SQLException e) {
            System.out.println("Проблема с добавлением пользователя в список " + e.getMessage());
        }
        return bbusers;
    }

    public void cleanUsersTable() {
        String deleteSQL = "DELETE FROM bbusers";

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(deleteSQL);
            System.out.println("Таблица пользователей очищена успешно.");
        } catch (SQLException e) {
            System.out.println("Проблема с очищением таблицы пользователей " + e.getMessage());
        }
    }

    public void changeAgeById(long id, byte age) {
        String updateAgeSQL = "UPDATE bbusers SET age = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateAgeSQL)) {
            preparedStatement.setByte(1, age);
            preparedStatement.setLong(2, id);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Возраст пользователя с id " + id + " успешно обновлён.");
            } else {
                System.out.println("Пользователь с id " + id + " не найден.");
            }
        } catch (SQLException e) {
            System.out.println("Проблема с изменением возраста пользователя " + e.getMessage());
        }

    }
}

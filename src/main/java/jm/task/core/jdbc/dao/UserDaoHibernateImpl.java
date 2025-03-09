package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    Util util = Util.getInstance();
    SessionFactory sessionFactoryDao = util.getSessionFactory();

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactoryDao.getCurrentSession()) {
            transaction = session.beginTransaction();
            String sql = "CREATE TABLE IF NOT EXISTS bbusers (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR(50), " +
                    "lastName VARCHAR(50), " +
                    "age TINYINT)";
            session.createNativeQuery(sql).executeUpdate();
            transaction.commit();
            System.out.println("Таблица bbusers успешно создана.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                System.out.println("Ошибка при создании таблицы bbusers.");
            }
            System.out.println("Проблема с транзакцией создания таблицы: " + e.getMessage());
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactoryDao.getCurrentSession()) {
            transaction = session.beginTransaction();
            String sql = "DROP TABLE IF EXISTS bbusers";
            session.createNativeQuery(sql).executeUpdate();
            transaction.commit();
            System.out.println("Таблица bbusers успешно удалена.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                System.out.println("Ошибка при удалении таблицы bbusers.");
            }
            System.out.println("Проблема с транзакцией удаления таблицы: " + e.getMessage());
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = sessionFactoryDao.getCurrentSession()) {
            transaction = session.beginTransaction();
            User user = new User();
            user.setName(name);
            user.setLastName(lastName);
            user.setAge(age);
            session.save(user);
            transaction.commit();
            System.out.println("Пользователь " + name + " " + lastName + " " + age + " успешно сохранен в таблицу.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                System.out.println("Пользователь " + name + " " + lastName + " " + age + " НЕ сохранен в таблицу.");
            }
            System.out.println("Проблема с транзакцией записи пользователя " + e.getMessage());
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = sessionFactoryDao.getCurrentSession()) {
            transaction = session.beginTransaction();
            String hql = "DELETE FROM User u WHERE u.id = :userId";
            Query query = session.createQuery(hql);
            query.setParameter("userId", id);
            int result = query.executeUpdate();
            transaction.commit();
            System.out.println("Пользователь с id " + id + " успешно удален.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                System.out.println("Ошибка при удалении пользователя с id " + id);
            }
            System.out.println("Проблема с транзакцией удаления пользователя по id: " + e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        List<User> users = null;
        try (Session session = sessionFactoryDao.getCurrentSession()) {
            transaction = session.beginTransaction();
            users = session.createQuery("from User").getResultList();
            transaction.commit();
            System.out.println("Все пользователи выгружены из базы.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Проблема с транзакцией создания списка пользователей " + e.getMessage());
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactoryDao.getCurrentSession()) {
            transaction = session.beginTransaction();
            String hql = "DELETE FROM User";
            Query query = session.createQuery(hql);
            int result = query.executeUpdate();
            transaction.commit();
            System.out.println("Все пользователи из таблицы. Удалено записей: " + result);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                System.out.println("Ошибка при удалении всех пользователей.");
            }
            System.out.println("Проблема с транзакцией очистки таблицы: " + e.getMessage());
        }
    }

    @Override
    public void changeAgeById(long id, byte age) {
        Transaction transaction = null;
        try (Session session = sessionFactoryDao.getCurrentSession()) {
            transaction = session.beginTransaction();
            String hql = "UPDATE User u SET u.age = :newAge WHERE u.id = :userId";
            Query query = session.createQuery(hql);
            query.setParameter("newAge", age);
            query.setParameter("userId", id);
            int result = query.executeUpdate();
            transaction.commit();
            System.out.println("Возраст пользователя с id " + id + " успешно обновлен на " + age + ".");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                System.out.println("Ошибка при обновлении возраста пользователя с id " + id);
            }
            System.out.println("Проблема с транзакцией обновления возраста пользователя: " + e.getMessage());
        }
    }
}

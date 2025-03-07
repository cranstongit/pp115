package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.SessionFactory;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

//    Util util = Util.getInstance();
//    static SessionFactory sessionFactory = null; //Ментор оставил замечание "Вынести SessionFactory в поле класса дао"

    public UserDaoHibernateImpl() {

    }




    @Override
    public void createUsersTable() {

    }

    @Override
    public void dropUsersTable() {

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
//        Session session = Util.getSessionFactory().openSession();
//        Transaction tx1 = session.beginTransaction();
//        session.save(user);
//        tx1.commit();
//        session.close();
    }

    @Override
    public void removeUserById(long id) {

    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public void cleanUsersTable() {

    }

    @Override
    public void changeAgeById(long id, byte age) {
//        Session session = Util.getSessionFactory().openSession();
//        Transaction tx1 = session.beginTransaction();
//        session.update(user);
//        tx1.commit();
//        session.close();
    }
}

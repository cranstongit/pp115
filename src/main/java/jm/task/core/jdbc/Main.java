package jm.task.core.jdbc;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.util.List;

public class Main {
    public static void main(String[] args) {
            List<User> users = null;
            UserService userService = new UserServiceImpl();
            User user1 = new User("Jessy", "Pinkman", (byte) 25);
            User user2 = new User("Walter", "White", (byte) 45);
            User user3 = new User("Gustavo", "Fring", (byte) 40);
            User user4 = new User("Saul", "Goodman", (byte) 35);

            userService.dropUsersTable();
            userService.createUsersTable();
            userService.saveUser(user1.getName(), user1.getLastName(), user1.getAge());
            userService.saveUser(user2.getName(), user2.getLastName(), user2.getAge());
            userService.saveUser(user3.getName(), user3.getLastName(), user3.getAge());
            userService.saveUser(user4.getName(), user4.getLastName(), user4.getAge());
            userService.changeAgeById(1L, (byte) 27);
            userService.removeUserById(3L);
            users = userService.getAllUsers();
            System.out.println(users);
            userService.cleanUsersTable();
            userService.dropUsersTable();
            Util.shutdown();
    }
}
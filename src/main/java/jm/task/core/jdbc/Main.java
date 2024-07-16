package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {

    private static UserService userService = new UserServiceImpl();

    public static void main(String[] args) {
        // реализуйте алгоритм здесь

        userService.dropUsersTable();
        userService.createUsersTable();

        userService.saveUser("Andrey","sds", (byte) 23);
        userService.saveUser("Oleg","wer", (byte) 23);
        userService.saveUser("Margo","qsc", (byte) 23);
        userService.saveUser("Anastasiya","cxsa", (byte) 23);

        userService.removeUserById(2);

        userService.getAllUsers().forEach(System.out::println);

//        userService.cleanUsersTable();
    }
}

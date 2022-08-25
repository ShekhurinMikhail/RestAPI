package ru.kata.spring.boot_security.demo.service;





import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;


public interface UserService {
    List<User> getAllUsers();

    void saveUser(User user);

    void updateUser(User user);

    void deleteUser(long id);
    User showUser(long id);
    User findUserByEmail(String email);
}

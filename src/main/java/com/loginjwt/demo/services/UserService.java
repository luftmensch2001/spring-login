package com.loginjwt.demo.services;

import com.loginjwt.demo.models.User;
import org.springframework.data.util.Pair;

public interface UserService {
    Iterable<User> getAllUsers();
    Pair<Boolean, Object> getUserById(Long id);
    Pair<Boolean, Object> createNewUser(User user);
    Pair<Boolean, Object> login(User user);
    Pair<Boolean, Object> deleteUser(Long id);

}
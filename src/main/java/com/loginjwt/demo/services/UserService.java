package com.loginjwt.demo.services;

import com.loginjwt.demo.models.ResponseObject;
import com.loginjwt.demo.models.User;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface UserService {
    Iterable<User> getAllUsers();
    Pair<Boolean, Object> getUserById(Long id);
    ResponseEntity<ResponseObject> createNewUser(User user);
    ResponseEntity<ResponseObject> login(User user);
    ResponseEntity<ResponseObject> deleteUser(Long id);

}

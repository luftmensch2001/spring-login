package com.loginjwt.demo.services;

import com.loginjwt.demo.models.ResponseObject;
import com.loginjwt.demo.models.User;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface UserService {
    Iterable<User> getAllUsers();
    ResponseEntity<ResponseObject> getUserById(Long id);
    ResponseEntity<ResponseObject> createNewUser(User user);
    ResponseEntity<ResponseObject> login(User user);
    ResponseEntity<ResponseObject> deleteUser(Long id);

}

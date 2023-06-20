package com.loginjwt.demo.controllers;

import com.loginjwt.demo.models.ResponseObject;
import com.loginjwt.demo.models.User;
import com.loginjwt.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/")
public class UserController {
    @Autowired
    private UserService userService;
//    Get all users
    @GetMapping("")
    public Iterable<User> getAllUsers() {
        return userService.getAllUsers();
    }
//    Get user by id
    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }
//    Create new user
    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createNewUser(@RequestBody User user) {
        return userService.createNewUser(user);
    }
//    Create new user
    @PostMapping("/login")
    public ResponseEntity<ResponseObject> login(@RequestBody User user) {
        return userService.login(user);
    }
//    Delete an user
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }
}

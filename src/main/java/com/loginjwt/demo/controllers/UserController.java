package com.loginjwt.demo.controllers;

import com.loginjwt.demo.DTO.LoginDTO;
import com.loginjwt.demo.models.User;
import com.loginjwt.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/users/")
public class UserController {
    @Autowired
    private UserService userService;
    //    Get all users
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }
    //    Get user by id
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        Pair<Boolean, Object> result = userService.getUserById(id);
        if (result.getFirst()) {
            return new ResponseEntity<>(result.getSecond(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result.getSecond(), HttpStatus.BAD_REQUEST);
        }
    }
    //    Create new user
    @PostMapping("/register")
    public ResponseEntity<?> createNewUser(@RequestBody User user) {
        Pair<Boolean, Object> result = userService.createNewUser(user);
        if (result.getFirst()) {
            return new ResponseEntity<>(result.getSecond(), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(result.getSecond(), HttpStatus.BAD_REQUEST);
        }
    }
    //    Create new user
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Pair<Boolean, LoginDTO> result = userService.login(user);
        if (result.getFirst()) {
            return new ResponseEntity<>(result.getSecond(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result.getSecond(), HttpStatus.UNAUTHORIZED);
        }
    }
    //    Delete an user
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        Pair<Boolean, Object> result = userService.deleteUser(id);
        if (result.getFirst()) {
            return new ResponseEntity<>(result.getSecond(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result.getSecond(), HttpStatus.BAD_REQUEST);
        }
    }
}
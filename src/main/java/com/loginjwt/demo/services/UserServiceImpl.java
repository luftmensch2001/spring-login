package com.loginjwt.demo.services;

import com.loginjwt.demo.models.ResponseObject;
import com.loginjwt.demo.models.User;
import com.loginjwt.demo.repositories.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

//    List all users
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
//    Get user by id
    @Override
    public Pair<Boolean, Object> getUserById(Long id) {
        User user = userRepository.findById(id);
        if (user != null) {
            return Pair.of(true, user);
        } else {
            return Pair.of(false, "Not found this user");
        }
    }
//    Create new user
    @Override
    public ResponseEntity<ResponseObject> createNewUser(User user) {
//        Username already existed
        User foundUser = userRepository.findByUsername(user.getUsername());
        if (foundUser != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                new ResponseObject("FAILED", "Username " + user.getUsername() + " is already existed")
            );
        }
//        Store new user to database
        String encodedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(8));
        user.setPassword(encodedPassword);
        return ResponseEntity.status(HttpStatus.OK).body(
            new ResponseObject("OK", "Create successfully", userRepository.save(user))
        );
    }
//    Login check
    @Override
    public ResponseEntity<ResponseObject> login(User user) {
        User foundUser = userRepository.findByUsername(user.getUsername());
        if (foundUser == null)  {
//            Cannot found user
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("FAILED", "User not found")
            );
        } else {
            if (BCrypt.checkpw(user.getPassword(), foundUser.getPassword())) {
//                Password is correct
                return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("OK", "Login successfully")
                );
            } else {
//                Password is incorrect
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("FAILED", "Incorrect password")
                );
            }
        }
    }
//    Delete an user by id
    @Override
    public ResponseEntity<ResponseObject> deleteUser(Long id) {
        userRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("OK", "Deleted user")
        );
    }
}

package com.loginjwt.demo.services;

import com.loginjwt.demo.models.ResponseObject;
import com.loginjwt.demo.models.User;
import com.loginjwt.demo.repositories.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

//    List all users
    @Override
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }
//    Get user by id
    @Override
    public ResponseEntity<ResponseObject> getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("OK", "Successfully", user)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("FAILED", "Not found this user")
            );
        }
    }
//    Create new user
    @Override
    public ResponseEntity<ResponseObject> createNewUser(User user) {
//        Username already existed
        if (userRepository.findByUsername(user.getUsername()).size() > 0) {
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
        List<User> foundUsers = userRepository.findByUsername(user.getUsername());
        if (foundUsers.size() == 0)  {
//            Cannot found user
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("FAILED", "User not found")
            );
        } else {
            User foundUser = foundUsers.get(0);
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

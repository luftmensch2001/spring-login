package com.loginjwt.demo.servicesImpl;

import com.loginjwt.demo.models.User;
import com.loginjwt.demo.repositories.UserRepository;
import com.loginjwt.demo.services.UserService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
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
    public Pair<Boolean, Object> createNewUser(User user) {
//        Username already existed
        User foundUser = userRepository.findByUsername(user.getUsername());
        if (foundUser != null) {
            return Pair.of(false, "Username " + user.getUsername() + " is already existed");
        }
//        Store new user to database
        String encodedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(8));
        user.setPassword(encodedPassword);
        return Pair.of(true, userRepository.save(user));
    }
//    Login check
    @Override
    public Pair<Boolean, Object> login(User user) {
        User foundUser = userRepository.findByUsername(user.getUsername());
        if (foundUser == null)  {
//            Cannot found user
            return Pair.of(false, "User not found");
        } else {
            if (BCrypt.checkpw(user.getPassword(), foundUser.getPassword())) {
//                Password is correct
                return Pair.of(true, "Login successfully");
            } else {
//                Password is incorrect
                return Pair.of(false, "Incorrect password");
            }
        }
    }
//    Delete an user by id
    @Override
    public Pair<Boolean, Object> deleteUser(Long id) {
        userRepository.deleteById(id);
        return Pair.of(true, "Deleted user");
    }
}

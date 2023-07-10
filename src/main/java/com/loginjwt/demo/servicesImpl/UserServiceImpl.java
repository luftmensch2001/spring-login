package com.loginjwt.demo.servicesImpl;

import com.loginjwt.demo.models.User;
import com.loginjwt.demo.repositories.UserRepository;
import com.loginjwt.demo.security.jwt.JwtService;
import com.loginjwt.demo.models.Role;
import com.loginjwt.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //    List all users
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    //    Get user by id
    @Override
    public Pair<Boolean, Object> getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return Pair.of(true, user.get());
        } else {
            return Pair.of(false, "Not found this user");
        }
    }
    //    Create new user
    @Override
    public Pair<Boolean, Object> createNewUser(User user) {
//        Username already existed
        Optional<User> foundUser = userRepository.findByUsername(user.getUsername());
        if (foundUser.isPresent()) {
            return Pair.of(false, "Username " + user.getUsername() + " is already existed");
        }
//        Store new user to database
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.ADMIN);
        userRepository.save(user);
        String token = jwtService.generateToken(user);
        return Pair.of(true, "Token: " + token);
    }
    //    Login check
    @Override
    public Pair<Boolean, Object> login(User user) {
        Optional<User> foundUser = userRepository.findByUsername(user.getUsername());
        if (!foundUser.isPresent())  {
//            Cannot found user
            return Pair.of(false, "User not found");
        } else {
//            Check authentication
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(),
                            user.getPassword()
                    )
            );
            var jwtToken = jwtService.generateToken(foundUser.get());
            return Pair.of(true, "Token: " + jwtToken);
        }
    }
    //    Delete an user by id
    @Override
    public Pair<Boolean, Object> deleteUser(Long id) {
        userRepository.deleteById(id);
        return Pair.of(true, "Deleted user");
    }
}
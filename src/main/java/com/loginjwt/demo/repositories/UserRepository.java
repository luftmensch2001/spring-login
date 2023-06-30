package com.loginjwt.demo.repositories;

import com.loginjwt.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;

@Repository
public class UserRepository {
    public static final String HASH_KEY = "User";

    @Autowired
    private RedisTemplate template;

//    Get all
    public List<User> findAll() {
        return template.opsForHash().values(HASH_KEY);
    }

//    Find user by id
    public User findById(Long id) {
        return (User) template.opsForHash().get(HASH_KEY, id);
    }

//    Find user by username
    public User findByUsername(String username) {
        List<User> allUsers = findAll();
        for (int i = 0; i < allUsers.size(); i++) {
            User user = allUsers.get(i);
            if (user.getUsername().equals(username)) return user;
        }
        return null;
    }

//    Create new user
    public User save(User newUser) {
        List<User> listUser = findAll();
//        Find current max id
        Long maxId = 0l;
        if (listUser.size() > 0)
            maxId = listUser.stream().max(Comparator.comparing(User::getId)).get().getId();
//        Increase id for new user
        newUser.setId(maxId + 1);
//        Save to cache
        template.opsForHash().put(HASH_KEY, newUser.getId(), newUser);
        return newUser;
    }

//    Create new user
    public void deleteById(Long id) {
        template.opsForHash().delete(HASH_KEY, id);
    }

}

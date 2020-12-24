package com.cursor.bugtracker.dao;

import com.cursor.bugtracker.interfaces.Singleton;
import com.cursor.bugtracker.model.User;

import java.util.*;

public class UserInMemoryDao implements UserDao, Singleton {

    private static UserInMemoryDao instance;

    public static UserInMemoryDao getInstance(){
        if(instance == null)
            instance = new UserInMemoryDao();
        return instance;
    }

    private Map<String, User> users = new HashMap<>();

    public UserInMemoryDao() {
        String id1 = "5a817bdd-8294-4b05-a8f6-dc0e631563e5";
        User user1 = new User(id1, "taras", "password");

        String id2 = "a6a749cb-8e8b-4471-a63c-c59004a7ccf5";
        User user2 = new User(id1, "john", "password");

        users.put(id1, user1);
        users.put(id2, user2);
    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public User save(User user) {
        users.put(user.getUserId(), user);
        return user;
    }

    @Override
    public User getUserById(String userId) {
        return users.get(userId);
    }

    @Override
    public User deleteUserById(String userId) {
        return users.remove(userId);
    }

    @Override
    public User getUserByUsername(String username) {
        for (User user : getAllUsers()) {
            if (user.getName().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public List<User> findByName(String query) {
        final List<User> result = new LinkedList<>();

        //// find tickets

        return result;
    }
}

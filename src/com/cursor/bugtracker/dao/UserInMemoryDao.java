package com.cursor.bugtracker.dao;

import com.cursor.bugtracker.interfaces.Singleton;
import com.cursor.bugtracker.model.Ticket;
import com.cursor.bugtracker.model.User;

import java.util.*;

public class  UserMemoryDao {
    static private UserMemoryDao instance;

    public static UserMemoryDao getInstance() {
        if (instance == null) {
            instance = new UserMemoryDao();
        }
        return instance;
    }

   public class UsersIn implements UserDao {
        private Map<String, User> users = new HashMap<>();

        public UsersIn() {
            String id1 = "5a817bdd-8294-4b05-a8f6-dc0e631563e5";
            User user1 = new User(id1, "taras", "password");

            String id2 = "7c2a397b-3634-426f-bf57-4a9bda5d6ede";
            User user2 = new User(id2, "john", "password");

            String id3 = "4d87fd3a-ae9c-4834-a166-c745a8c92cda ";
            User user3 = new User(id3, "jake", "password");

            String id4 = "9e7c6150-54f4-4f9e-a706-1412d65eb03a";
            User user4 = new User(id4, "frank", "password");

            String id5 = "be3c0e68-d35c-4960-9c88-82f789bbcd06";
            User user5 = new User(id5, "kyle", "password");

            users.put(id1, user1);
            users.put(id2, user2);
            users.put(id3, user3);
            users.put(id4, user4);
            users.put(id5, user5);
        }

        @Override
        public Map<String, User> getAllUsers() {
            return users;
        }


        @Override
        public User save(User user) {
            users.put(user.getUserId(), user);
            return user;
        }


        @Override
        public User getUserId(String userId) {
            return users.get(userId);
        }

        @Override
        public User deleteUserId(String userId) {
            return users.remove(userId);
        }

        @Override
        public User saveName(User username) {
            users.put(username.getName(), username);
            return username;
        }

        @Override
        public User getName(String username) {
            return users.get(username);
        }

        @Override
        public User deletedName(String username) {
            return users.remove(username);

        }

        @Override
        public User savePassword(User password) {
            users.put(password.getPassword(), password);
            return password;

        }


        @Override
        public User getPassword(int password) {
            return users.get(password);
        }

        @Override
        public User deletedPassword(int password) {
            return users.remove(password);

        }

        public List<User> findByName(String query) {
            final List<User> result = new LinkedList<>();
            for (User users : result) {
                if (users.getName().equals(query)) {
                    return result;
                }
                //// find tickets


            }

            return null;
        }

        public String getByUsername(String username) {
           final List<User> user = new LinkedList<>();
            if (users.containsValue(username))
            return username;


            return username;
        }

    }
}

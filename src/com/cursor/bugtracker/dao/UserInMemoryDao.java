package com.cursor.bugtracker.dao;

import com.cursor.bugtracker.model.Ticket;
import com.cursor.bugtracker.model.User;

import java.util.*;

// make it singleton !!!
public class UserInMemoryDao implements UserDao {

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
    public User save(User user) {
        users.put(user.getUserId(), user);
        return user;
    }

    @Override
    public User getById(String ticketId) {
        return tickets.get(ticketId);
    }

    @Override
    public boolean removeById(String ticketId) {

    }

    public List<User> findByName(String query) {
        final List<User> result = new LinkedList<>();

        //// find tickets

        return result;
    }
}

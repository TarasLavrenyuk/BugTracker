package com.cursor.bugtracker.dao;

import com.cursor.bugtracker.model.User;

import java.util.List;

public interface UserDao {

    List<User> getAllUsers();

    User save(User user);

    User getUserById(String userId);

    User deleteUserById(String userId);

    User getUserByUsername(String username);

    List<String> getUsernamesByIds(List<String> userIds);
}

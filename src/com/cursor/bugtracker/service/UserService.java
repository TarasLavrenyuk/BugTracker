package com.cursor.bugtracker.service;

import com.cursor.bugtracker.model.User;

// make it singleton
public class UserService {

    public User createUser(String username, String password) throws UsernameAlreadyTakenException {
        // validate inputs

        // check if username is available

        final User newUser = new User(username, password);

        userDao.save(newUser);

        return newUser;
    }

    public User login(String username, String password) {
        // validate username

        // dao.getUserByUsername(username)

        // if null throw BadCredentialsException

        // if user.password != password throw BadCredentialsException

        return user;
    }

}

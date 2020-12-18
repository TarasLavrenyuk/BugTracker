package com.cursor.bugtracker.service;

import com.cursor.bugtracker.dao.UserDao;
import com.cursor.bugtracker.dao.UserInMemoryDao;
import com.cursor.bugtracker.exceptions.*;
import com.cursor.bugtracker.interfaces.Singleton;
import com.cursor.bugtracker.model.User;

import java.util.regex.Pattern;

public class UserService implements Singleton {

    private UserDao userDao = UserInMemoryDao.getInstance();


    private static UserService instance;

    private UserService() {
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public User createUser(final String username, final String password) throws UserNameAlreadyTakenException,
            UnacceptableUsernameException, UnacceptablePasswordException {
        String validatedUsername = username.trim();
        validateUsername(validatedUsername);
        if (userDao.getUserByUsername(validatedUsername) != null) {
            throw new UserNameAlreadyTakenException("Username \"" + username + "\" is already taken.");
        }
        return userDao.save(new User(username, password));
    }

    public User login(String username, String password) {
        // validate username

        // dao.getUserByUsername(username)

        // if null throw BadCredentialsException

        // if user.password != password throw BadCredentialsException

        return user;
    }

    public void validateUsername(final String username) throws UnacceptableUsernameException {
        Pattern pattern = Pattern.compile("[!@#$%^&*()+=';:?><,|№/ ]");
        if (pattern.matcher(username).find()) {
            throw new UnacceptableUsernameException("Username unacceptable." +
                    " You can use only letters, digits and elements: ._-");
        }

        // username must start with letter (regex)
        // https://ru.wikipedia.org/wiki/%D0%A0%D0%B5%D0%B3%D1%83%D0%BB%D1%8F%D1%80%D0%BD%D1%8B%D0%B5_%D0%B2%D1%8B%D1%80%D0%B0%D0%B6%D0%B5%D0%BD%D0%B8%D1%8F
    }


    // TODO: move to FE
    public String validatePasswordString(final String password) throws UnacceptablePasswordException {
        String validatedPassword = password.trim();
        if (validatedPassword.length() < 8) {
            throw new UnacceptablePasswordException("Your password must be not less then eight elements");
        }
        return password;
    }


    // TODO: move to FE
    /**
     * Checks if password has at least 3 symbols
     */
    public void checkPassword(final String password) throws UnacceptablePasswordException {
        int countOfDigit = 0;
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) countOfDigit++;
        }
        if (countOfDigit < 3) {
            throw new UnacceptablePasswordException("Password is not correct. " +
                    "You must use not less then three digits");
        }
    }

}
package com.cursor.bugtracker.service;

import com.cursor.bugtracker.dao.UserDao;
import com.cursor.bugtracker.dao.UserInMemoryDao;
import com.cursor.bugtracker.exceptions.*;
import com.cursor.bugtracker.interfaces.Singleton;
import com.cursor.bugtracker.model.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
            UnacceptableUsernameException {
        String validatedUsername = username.trim();
        validateUsername(validatedUsername);
        if (userDao.getUserByUsername(validatedUsername) != null) {
            throw new UserNameAlreadyTakenException("Username \"" + username + "\" is already taken.");
        }
        return userDao.save(new User(username, password));
    }

    public User login(String username, String password) throws BadCredentialsException {
        // validate username
        String validateUsername = username.trim();

        if (userDao.getUserByUsername(validateUsername) == null) {
            throw new BadCredentialsException("Username " + validateUsername + " does not exist");
        }
        if (!password.equals(userDao.getUserByUsername(validateUsername).getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }
        return userDao.getUserByUsername(validateUsername);
    }

    public void validateUsername(final String username) throws UnacceptableUsernameException {
        Pattern pattern = Pattern.compile("[!@#$%^&*()+=';:?><,|№/ ]");
        if (pattern.matcher(username).find()) {
            throw new UnacceptableUsernameException("Username unacceptable." +
                    " You can use only letters, digits and elements: ._-");
        }
        if (!Character.isLetter(username.charAt(0))) {
            throw new UnacceptableUsernameException("Username must start with a small letter of english alphabet.");
        }
    }

    /**
     * @deprecated please use UserService.checkIfUsersExist
     */
    public List<String> findByName(final String[] name) {
        List<String> namesExist = new ArrayList<>();
        for (String tempName : name) {
            if (userDao.getUserByUsername(tempName) != null) {
                namesExist.add(tempName);
            } else {
                return null;
            }
        }
        return namesExist;
    }

    public boolean checkIfUsersExist(final List<String> usernames) throws WrongUsernameException {
        for (String username : usernames) {
            if (userDao.getUserByUsername(username) == null) {
                throw new WrongUsernameException("User with username  \"" + username + "\" does not exists.");
            }
        }
        return true;
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

    /**
     * @return user sorted by username
     */
    public List<User> getAllUsers() {
        return userDao.getAllUsers().stream()
                .sorted(Comparator.comparing(User::getName))
                .collect(Collectors.toList());
    }
}
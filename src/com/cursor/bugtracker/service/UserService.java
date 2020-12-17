package com.cursor.bugtracker.service;

import com.cursor.bugtracker.exceptions.UnacceptableUsernameException;
import com.cursor.bugtracker.exceptions.UnacceptablePasswordException;
import com.cursor.bugtracker.exceptions.UserNameAlreadyTakenException;
import com.cursor.bugtracker.model.User;

public class UserService {

    private static UserService instance;

    private UserService() {
    };

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public User createUser(String username, String password) throws UserNameAlreadyTakenException,
            UnacceptableUsernameException, UnacceptablePasswordException {
        // validate inputs
        String validatedUsername = validateOfUsernameString(username);
        String validatedPassword = validateOfPasswordString(password);

        // check if username is available
        checkUsername(validatedUsername);
        checkPassword(validatedPassword);

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

    public String validateOfUsernameString(final String username) throws UnacceptableUsernameException {
        String validatedUsername = username.trim();
        if (validatedUsername.length() < 6) {
            throw new UnacceptableUsernameException("Length of name must be not less then six elements.");
        }
        return validatedUsername;
    }

    // this method checks the number of letters and the absence of invalid characters
    public void checkUsername(String username) throws UnacceptableUsernameException {
        int countOfLetters = 0;
        String[] res = username.split("");
        for (int i = 0; i < res.length; i++) {
            if ("[!@#$%^&*()+=';:?><,|№/ ]".contains(res[i])) {
                throw new UnacceptableUsernameException("Username unacceptable." +
                        " You can use only letters, digits and elements: ._-"
                        + System.lineSeparator() + "Please repeat one more");
            }
            char c = res[i].charAt(0);
            if (Character.isLetter(c)) {
                countOfLetters++;
            }
        }
        if (countOfLetters < 1) {
            throw new UnacceptableUsernameException("Name must be have not less then one letter");
        }
    }


    public String validateOfPasswordString(final String password) throws UnacceptablePasswordException {
        String validatedPassword = password.trim();
        if (validatedPassword.length() < 8) {
            throw new UnacceptablePasswordException("Your password must be not less then eight elements");
        }
        return password;
    }

    // this method checks that the password is more than two digits
    public void checkPassword(String password) throws UnacceptablePasswordException {
        int countOfDigit = 0;
        String[] arrOfPassword = password.split("");
        for (int i = 0; i < arrOfPassword.length; i++) {
            char c = arrOfPassword[i].charAt(0);
            if (Character.isDigit(c)) {
                countOfDigit++;
            }
        }
        if (countOfDigit < 3) {
            throw new UnacceptablePasswordException("Password is not correct. " +
                    "You must use not less then three digits");
        }
    }

}
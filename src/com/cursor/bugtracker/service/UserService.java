package com.cursor.bugtracker.service;

import bugtracker.exceptions.NameIsNotCorrectException;
import bugtracker.exceptions.PassIsNotCorrectException;
import bugtracker.exceptions.UserNameAlreadyTakenException;
import com.cursor.bugtracker.model.User;

// make it singleton
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
            NameIsNotCorrectException, PassIsNotCorrectException {
        // validate inputs
        username = validateOfString(username);
        password = validateOfPassString(password);

        // check if username is available
        checkInputUserName(username);
        checkInputPass(password);

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

    public void checkInputUserName(String name) throws NameIsNotCorrectException {
        int countOfLetters = 0;
        char[] res = name.toCharArray();
        for (int i = 0; i < res.length; i++) {
            if (Character.isLetter(res[i])) {
                countOfLetters++;
            }
        }
        if (countOfLetters < 1) {
            throw new NameIsNotCorrectException("Name must be have not less then one letter");
        }
    }

    public String validateOfString(String word) throws NameIsNotCorrectException {
        word = word.trim().replaceAll("[!@#$%^&*()+=';:?><,|№/ ]", "");
        if (word.length() < 6) {
            throw new NameIsNotCorrectException("Length of name must be not less then six elements." +
                    " You can use only letters, digits and elements: ._-"
                    + System.lineSeparator() + "Please repeat one more");
        }
        return word;
    }

    public String validateOfPassString(String pass) throws PassIsNotCorrectException {
        pass = pass.trim().replaceAll(" ", "");
        if (pass.length() < 8) {
            throw new PassIsNotCorrectException("Your password must be not less then eight elements");
        }
        return pass;
    }

    public void checkInputPass(String pass) throws PassIsNotCorrectException {
        int countOfDigit = 0;
        String[] arrOfPass = pass.split("");
        for (int i = 0; i < arrOfPass.length; i++) {
            if ("!@#$%^&*()=+]{}[';:?/|',".contains(arrOfPass[i])) {
                throw new PassIsNotCorrectException("Password is not correct. " +
                        "You can use only letters, digits and elements: .-_");
            }
            char c = arrOfPass[i].charAt(0);
            if (Character.isDigit(c)) {
                countOfDigit++;
            }
        }
        if (countOfDigit < 3) {
            throw new PassIsNotCorrectException("Password is not correct. " +
                    "You must use not less then three digits");
        }
    }

}

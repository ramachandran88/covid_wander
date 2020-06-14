package com.wander.exception;

import static java.text.MessageFormat.format;

public class UserAlreadyExistException extends Exception {
    public UserAlreadyExistException(String email) {
        super(format("User with email {0} already exist.", email));
    }
}

package com.wander.exception;

import com.wander.ui.model.UserRequest;

import static java.text.MessageFormat.format;

public class UserAlreadyExistException extends Exception {
    public UserAlreadyExistException(UserRequest userRequest) {
        super(format("User with email {0} already exist.", userRequest.getEmail()));
    }
}

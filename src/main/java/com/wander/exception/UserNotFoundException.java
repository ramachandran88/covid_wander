package com.wander.exception;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static java.text.MessageFormat.format;

public class UserNotFoundException extends UsernameNotFoundException {
    public UserNotFoundException(String username) {
        super(format("User with email {0} cannot be found.", username));
    }
}
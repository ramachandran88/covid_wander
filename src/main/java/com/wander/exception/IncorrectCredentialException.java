package com.wander.exception;

import static java.text.MessageFormat.format;

public class IncorrectCredentialException extends Exception {
    public IncorrectCredentialException(String email) {
        super(format("Access Denied for {0}", email));
    }
}

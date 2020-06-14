package com.wander.controller;

import com.wander.exception.IncorrectCredentialException;
import com.wander.exception.UserAlreadyExistException;
import com.wander.service.UserService;
import com.wander.ui.model.UserRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
public class UserController {
    private UserService userService;
    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/sign-in", produces = "application/json")
    Map<String, String> signIn(@RequestBody UserRequest user) throws IncorrectCredentialException {
        logger.info("---------inside post sign in --------");
        userService.validateLogin(user.getEmail(), user.getPassword());
        return Collections.singletonMap("success", "true");
    }

    @PostMapping(value = "/sign-up",  produces = "application/json")
    Map<String, String> signUp(@RequestBody UserRequest user) throws UserAlreadyExistException {
        logger.info("-------------in sign up flow------------");
        userService.signUpUser(user);
        return Collections.singletonMap("success", "true");
    }


}

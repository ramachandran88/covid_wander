package com.wander.ui.controller;

import com.wander.exception.UserAlreadyExistException;
import com.wander.service.UserService;
import com.wander.ui.model.UserRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserViewController {
    private UserService userService;
    private final static Logger logger = LoggerFactory.getLogger(UserViewController.class);

    @Autowired
    public UserViewController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/index")
    String index(Model model) {
        model.addAttribute("user", new UserRequest());
        return "index";
    }

    @GetMapping("/sign-in")
    String signIn(Model model) {
        model.addAttribute("user", new UserRequest());
        return "sign-in";
    }

    @GetMapping("/sign-up")
    String signUp(Model model) {
        model.addAttribute("user", new UserRequest());
        return "sign-up";
    }

    @PostMapping("/sign-up")
    String signUp(UserRequest user) throws UserAlreadyExistException {
        logger.info("-------------in sign up flow------------");
        userService.signUpUser(user);
        return "redirect:/sign-in";
    }

}

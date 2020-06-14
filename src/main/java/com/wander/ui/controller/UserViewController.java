package com.wander.ui.controller;

import com.wander.ui.model.UserRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserViewController {
    private final static Logger logger = LoggerFactory.getLogger(UserViewController.class);

    @GetMapping(value = {"/", "/sign-in"})
    String signIn(Model model) {
        model.addAttribute("user", new UserRequest());
        return "sign-in";
    }

    @GetMapping("/sign-up")
    String signUp(Model model) {
        model.addAttribute("user", new UserRequest());
        return "sign-up";
    }


}

package com.wander.ui.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardViewController {
    private final static Logger logger = LoggerFactory.getLogger(DashboardViewController.class);

    public DashboardViewController() {
    }

    @GetMapping("/dashboard")
    String signIn() {
        return "dashboard";
    }

}

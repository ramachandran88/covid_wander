package com.wander.ui.controller;

import com.wander.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DashboardViewController.class)
class DashboardViewControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;
    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @WithMockUser(value = "user")
    @Test
    public void givenAuthRequestOnDashboard_shouldSucceedWith200() throws Exception {
        mvc.perform( get("/dashboard"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"));
    }

    @Test
    public void givenNoAuthRequestOnDashboard_shouldFailWith302() throws Exception {
        mvc.perform( get("/dashboard"))
                .andExpect(status().isUnauthorized());
    }


}
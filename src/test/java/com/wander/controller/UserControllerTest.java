package com.wander.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wander.exception.IncorrectCredentialException;
import com.wander.exception.UserAlreadyExistException;
import com.wander.exception.UserNotFoundException;
import com.wander.service.UserService;
import com.wander.ui.model.UserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserService userService;
    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    void signIn() throws Exception {
        mvc.perform(post("/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new UserRequest(null, "email", "pass"))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void signIn_shouldThrowUserNotFoundException_404() throws Exception {
        doThrow(new UserNotFoundException("email")).when(userService).validateLogin("email","pass");
        mvc.perform(post("/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new UserRequest(null, "email", "pass"))))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void signIn_shouldThrowIncorrectCredentialsException_400() throws Exception {
        doThrow(new IncorrectCredentialException("email")).when(userService).validateLogin("email","pass");
        mvc.perform(post("/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new UserRequest(null, "email", "pass"))))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void signUp() throws Exception {
        mvc.perform(post("/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new UserRequest("name", "email", "pass"))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    void signUp_UserAlreadyExist_409() throws Exception {
        UserRequest userRequest = new UserRequest("name", "email", "pass");
        doThrow(new UserAlreadyExistException("email")).when(userService).signUpUser(userRequest);
        mvc.perform(post("/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }
}
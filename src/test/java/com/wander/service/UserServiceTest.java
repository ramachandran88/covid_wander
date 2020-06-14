package com.wander.service;

import com.wander.db.model.User;
import com.wander.db.repository.UserRepository;
import com.wander.exception.IncorrectCredentialException;
import com.wander.exception.UserAlreadyExistException;
import com.wander.ui.model.UserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository, bCryptPasswordEncoder);
    }

    @Test
    void loadUserByUsername() {
        User expectedUser = new User("ram", "email", "password");
        when(userRepository.findByEmail("email")).thenReturn(Optional.of(expectedUser));
        User actual = (User) userService.loadUserByUsername("email");
        assertThat(actual).isEqualToComparingOnlyGivenFields(expectedUser,
                "name", "email", "password"
        );
    }

    @Test
    void shouldThrowUserNotFoundException() {
        when(userRepository.findByEmail("email")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> userService.loadUserByUsername("email"))
                .isInstanceOf(UsernameNotFoundException.class);
    }

    @Test
    void shouldValidateLogin() throws IncorrectCredentialException {
        when(userRepository.findByEmail("email")).thenReturn(Optional.of(new User("name", "email", "pass")));
        when(bCryptPasswordEncoder.matches("pass", "pass")).thenReturn(true);
        assertThat(userService.validateLogin("email", "pass")).isTrue();
    }

    @Test
    void shouldThrowIncorrectCredential() {
        when(userRepository.findByEmail("email")).thenReturn(Optional.of(new User("name", "email", "password")));
        when(bCryptPasswordEncoder.matches("pass", "password")).thenReturn(false);
        assertThatThrownBy(() -> userService.validateLogin("email", "pass"))
                .isInstanceOf(IncorrectCredentialException.class);
    }

    @Test
    void signUpUser() throws UserAlreadyExistException {
        when(bCryptPasswordEncoder.encode("password")).thenReturn("password");
        userService.signUpUser(new UserRequest("name", "email", "password"));
        ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1))
                .save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue()).isEqualToComparingOnlyGivenFields(
                new User("name", "email" , "password"),
                "name", "email" , "password"
        );
    }

    @Test
    void shouldThrowUserAlreadyExist() {
        when(userRepository.findByEmail("email"))
                .thenReturn(Optional.of(new User("name", "email", "password")));
        assertThatCode(() -> userService.signUpUser(new UserRequest("name", "email", "password")))
                .isInstanceOf(UserAlreadyExistException.class);
    }
}
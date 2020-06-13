package com.wander.db.repository;


import com.wander.db.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldFindByEmail(){
        User expectedUser = new User("ram", "a@bc.com", "pass");
        userRepository.save(expectedUser);
        assertThat(userRepository.findByEmail("a@bc.com")).isPresent()
                .hasValueSatisfying(user -> assertThat(user).isEqualToComparingOnlyGivenFields(expectedUser,
                "name", "email", "password"));
    }

    @Test
    void shouldReturnEmptyWhenUserNotFound(){
        User expectedUser = new User("ram", "a@bc.com", "pass");
        userRepository.save(expectedUser);

        assertThat(userRepository.findByEmail("notpresentemail")).isNotPresent();

    }

}
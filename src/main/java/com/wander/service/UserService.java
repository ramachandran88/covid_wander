package com.wander.service;

import com.wander.db.model.User;
import com.wander.db.repository.UserRepository;
import com.wander.exception.UserAlreadyExistException;
import com.wander.exception.UserNotFoundException;
import com.wander.ui.model.UserRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final static Logger logger = LoggerFactory.getLogger(UserService.class);
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UserNotFoundException {
        logger.info("-------------checking user details now------------");
        final Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new UserNotFoundException(email);
        }
    }

    public void signUpUser(UserRequest userRequest) throws UserAlreadyExistException {
        checkUserExist(userRequest);
        final String encryptedPassword = bCryptPasswordEncoder.encode(userRequest.getPassword());
        userRepository.save(new User(userRequest.getName(), userRequest.getEmail(), encryptedPassword));
    }

    private void checkUserExist(UserRequest userRequest) throws UserAlreadyExistException {
        final Optional<User> optionalUser = userRepository.findByEmail(userRequest.getEmail());
        if(optionalUser.isPresent()){
            throw new UserAlreadyExistException(userRequest);
        }
    }

}

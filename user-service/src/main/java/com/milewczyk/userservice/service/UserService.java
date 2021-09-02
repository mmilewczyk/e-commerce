package com.milewczyk.userservice.service;

import com.milewczyk.userservice.model.User;
import com.milewczyk.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findUserByUserName(String userName) {
        return userRepository.findByUsername(userName)
                .orElseThrow(() -> new IllegalArgumentException("User does not exist!"));
    }
}

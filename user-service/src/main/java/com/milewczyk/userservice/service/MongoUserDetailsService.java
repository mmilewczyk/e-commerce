package com.milewczyk.userservice.service;

import com.milewczyk.userservice.model.User;
import com.milewczyk.userservice.repository.UserRepository;
import com.milewczyk.userservice.model.MyUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MongoUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(userName);
        if (user.isPresent()) {
            return user.map(MyUserDetails::new).get();
        } else {
            throw new UsernameNotFoundException(userName + " not found");
        }
    }
}

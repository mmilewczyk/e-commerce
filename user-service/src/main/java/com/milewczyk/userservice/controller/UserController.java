package com.milewczyk.userservice.controller;

import com.milewczyk.userservice.model.User;
import com.milewczyk.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{userName}")
    public ResponseEntity<User> findUserByUserName(@PathVariable("userName") String userName) {
        return status(HttpStatus.OK).body(userService.findUserByUserName(userName));
    }
}

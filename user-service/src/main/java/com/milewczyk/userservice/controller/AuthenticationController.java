package com.milewczyk.userservice.controller;

import com.milewczyk.userservice.model.AuthenticationRequest;
import com.milewczyk.userservice.model.AuthenticationResponse;
import com.milewczyk.userservice.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping( "/authenticate")
    public ResponseEntity<AuthenticationResponse> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(authenticationService.createAuthenticationToken(authenticationRequest));
    }
}

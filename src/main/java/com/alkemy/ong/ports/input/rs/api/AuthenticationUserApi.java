package com.alkemy.ong.ports.input.rs.api;

import com.alkemy.ong.ports.input.rs.request.AuthenticationUserRequest;
import com.alkemy.ong.ports.input.rs.request.CreateUserRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Validated
public interface AuthenticationUserApi {


    @PostMapping("/createUser")
    ResponseEntity<?> createUser(@Valid @RequestBody CreateUserRequest createUserRequest) throws Exception;

    @PostMapping("/authenticationUser")
    ResponseEntity<?> authenticationUser(@RequestBody AuthenticationUserRequest authenticationUserRequest) throws Exception;
}

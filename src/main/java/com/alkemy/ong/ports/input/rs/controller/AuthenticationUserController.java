package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.common.exception.Message.ExceptionMessage;
import com.alkemy.ong.common.security.JwtUtils;
import com.alkemy.ong.domain.usecase.impl.UserServiceImpl;
import com.alkemy.ong.ports.input.rs.api.AuthenticationUserApi;
import com.alkemy.ong.ports.input.rs.request.AuthenticationUserRequest;
import com.alkemy.ong.ports.input.rs.request.CreateUserRequest;
import com.alkemy.ong.ports.input.rs.response.AuthenticationUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/authentication")
public class AuthenticationUserController implements AuthenticationUserApi {

    private UserServiceImpl userServiceImpl;

    private AuthenticationManager authenticationManager;

    private JwtUtils jwtTokenUtil;

    @Override
    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserRequest createUserRequest) throws Exception {
        userServiceImpl.create(createUserRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    @PostMapping("/authenticationUser")
    public ResponseEntity<?> authenticationUser(@RequestBody AuthenticationUserRequest authenticationUserRequest) throws Exception {

        UserDetails userDetails;

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationUserRequest.getEmail(),
                            authenticationUserRequest.getPassword()));

            userDetails = (UserDetails) authentication.getPrincipal();

        } catch (BadCredentialsException exception) {
            throw new Exception(ExceptionMessage.USERNAME_OR_PASSWORD_NOT_FOUND);
        }

        final String jwt = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationUserResponse(jwt));
    }
}

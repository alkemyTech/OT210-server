package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.common.security.JwtUtils;
import com.alkemy.ong.domain.usecase.UserService;
import com.alkemy.ong.ports.input.rs.request.AuthenticationRequest;
import com.alkemy.ong.ports.input.rs.response.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

import javax.validation.Valid;
import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthenticationRequest request) throws AccessDeniedException {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.username(),request.password()));

        if(authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails users){

            final  String token = jwtUtils.generateToken(users);

            return ResponseEntity.ok(AuthenticationResponse.builder()
                    .token(token)
                    .expirationDate(jwtUtils.extractExpirationDate(token))
                    .build());

        }
        throw new AccessDeniedException("error in the authenticatio process");


    }
}

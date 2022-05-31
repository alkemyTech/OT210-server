package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.common.security.JwtUtils;
import com.alkemy.ong.domain.model.User;
import com.alkemy.ong.ports.input.rs.request.AuthenticationRequest;
import com.alkemy.ong.ports.input.rs.response.AuthenticationResponse;
import com.alkemy.ong.ports.input.rs.response.MeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import javax.validation.Valid;
import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {


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
        throw new AccessDeniedException("error in the authentication process");

    }

    @GetMapping("/me")
    ResponseEntity<?> getUserInformation(@AuthenticationPrincipal User user) {

        return ResponseEntity.ok(MeResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole().getAuthority())
                .build());
    }
}
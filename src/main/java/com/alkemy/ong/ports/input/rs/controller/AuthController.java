package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.domain.model.User;
import com.alkemy.ong.ports.input.rs.response.MeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {


    private final AuthenticationManager authenticationManager;




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
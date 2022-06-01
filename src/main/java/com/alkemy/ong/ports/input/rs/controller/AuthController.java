package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.domain.model.User;
import com.alkemy.ong.ports.input.rs.mapper.UserControllerMapper;
import com.alkemy.ong.ports.input.rs.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserControllerMapper userMapper;


    @GetMapping("/me")
    public ResponseEntity<UserResponse> getUserInformation(@AuthenticationPrincipal User user) {

        UserResponse userResponse = userMapper.userToUserResponse(user);
        return  new ResponseEntity<>(userResponse, HttpStatus.OK);

    }
}
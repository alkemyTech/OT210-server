package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.common.security.JwtUtils;
import com.alkemy.ong.domain.model.User;
import com.alkemy.ong.domain.usecase.UserService;
import com.alkemy.ong.ports.input.rs.api.ApiConstants;
import com.alkemy.ong.ports.input.rs.mapper.UserControllerMapper;
import com.alkemy.ong.ports.input.rs.request.AuthenticationRequest;
import com.alkemy.ong.ports.input.rs.request.CreateUserRequest;
import com.alkemy.ong.ports.input.rs.response.AuthenticationResponse;
import com.alkemy.ong.ports.input.rs.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping(ApiConstants.AUTHENTICATION_URI)
@RequiredArgsConstructor
public class AuthController {

    private final UserControllerMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserService userService;


    @GetMapping("/me")
    public ResponseEntity<UserResponse> getUserInformation(@AuthenticationPrincipal User user) {
        UserResponse userResponse = userMapper.userToUserResponse(user);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthenticationRequest request) throws AccessDeniedException {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));

        if (authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails users) {

            final String token = jwtUtils.generateToken(users);

            return ResponseEntity.ok(AuthenticationResponse.builder()
                    .token(token)
                    .expirationDate(jwtUtils.extractExpiration(token))
                    .build());

        }
        throw new AccessDeniedException("error in the authentication process");
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerNewUser(@Valid @RequestBody CreateUserRequest userRequest) {

        User user = userMapper.createUserRequestToUser(userRequest);

        user = userService.registerNewUser(user);
        UserResponse response = userMapper.userToUserResponse(user);
        final long id = user.getId();

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(id)
                .toUri();

        return ResponseEntity.created(location)
                .body(response);

    }

}

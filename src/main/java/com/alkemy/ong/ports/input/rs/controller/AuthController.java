package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.common.security.JwtUtils;
import com.alkemy.ong.domain.model.User;
import com.alkemy.ong.domain.usecase.UserService;
import com.alkemy.ong.ports.input.rs.api.ApiConstants;
import com.alkemy.ong.ports.input.rs.api.AuthenticationApi;
import com.alkemy.ong.ports.input.rs.mapper.UserControllerMapper;
import com.alkemy.ong.ports.input.rs.request.AuthenticationRequest;
import com.alkemy.ong.ports.input.rs.request.CreateUserRequest;
import com.alkemy.ong.ports.input.rs.response.AuthenticationResponse;
import com.alkemy.ong.ports.input.rs.response.UserAndAuthenticationResponse;
import com.alkemy.ong.ports.input.rs.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
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

@RestController
@RequestMapping(ApiConstants.AUTHENTICATION_URI)
@RequiredArgsConstructor
public class AuthController implements AuthenticationApi {

    private final UserControllerMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserService userService;

    @Override
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getUserInformation(@AuthenticationPrincipal User user) {
        UserResponse userResponse = userMapper.userToUserResponse(user);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @Override
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody AuthenticationRequest request) {

        AuthenticationResponse response =
                prepareAuthenticationResponse(request.username(), request.password());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @PostMapping("/register")
    public ResponseEntity<UserAndAuthenticationResponse> registerNewUser(@Valid @RequestBody CreateUserRequest userRequest) {

        User user = userMapper.createUserRequestToUser(userRequest);
        user = userService.registerNewUser(user);

        UserResponse userResponse = userMapper.userToUserResponse(user);
        AuthenticationResponse authenticationResponse =
                prepareAuthenticationResponse(userRequest.getEmail(), userRequest.getPassword());

        var response = new UserAndAuthenticationResponse(userResponse, authenticationResponse);

        final long id = user.getId();

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(id)
                .toUri();

        return ResponseEntity.created(location)
                .body(response);

    }

    private AuthenticationResponse prepareAuthenticationResponse(
            String username, String password) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

        if (authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails users) {

            final String token = jwtUtils.generateToken(users);

            return AuthenticationResponse.builder()
                    .token(token)
                    .expirationDate(jwtUtils.extractExpiration(token))
                    .build();
        }

        throw new AccessDeniedException("error in the authentication process");
    }

}

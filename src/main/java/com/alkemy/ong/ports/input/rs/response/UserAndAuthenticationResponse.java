package com.alkemy.ong.ports.input.rs.response;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class UserAndAuthenticationResponse {
    UserResponse userResponse;
    AuthenticationResponse authenticationResponse;
}

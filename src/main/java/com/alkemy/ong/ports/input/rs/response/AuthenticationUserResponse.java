package com.alkemy.ong.ports.input.rs.response;

public class AuthenticationUserResponse {

    private final String jwt;

    public AuthenticationUserResponse(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }
}

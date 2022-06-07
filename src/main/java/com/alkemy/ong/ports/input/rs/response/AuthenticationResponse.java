package com.alkemy.ong.ports.input.rs.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Value;

import java.util.Date;

@Value
@Builder
public class AuthenticationResponse {

    String token;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss Z")
    Date expirationDate;
}
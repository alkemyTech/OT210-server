package com.alkemy.ong.domain.usecase;

import com.alkemy.ong.ports.input.rs.request.CreateUserRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;

public interface UserService extends UserDetailsService {

    void create(CreateUserRequest createUserRequest) throws IOException;
}

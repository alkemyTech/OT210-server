package com.alkemy.ong.ports.input.rs.api;

import com.alkemy.ong.ports.input.rs.response.UserResponseList;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Validated
public interface UserApi {

    ResponseEntity<UserResponseList> getUsers(Optional<Integer> page, Optional<Integer> size);

}

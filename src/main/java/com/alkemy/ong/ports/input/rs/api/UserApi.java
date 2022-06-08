package com.alkemy.ong.ports.input.rs.api;

import com.alkemy.ong.ports.input.rs.request.UpdateUserRequest;
import com.alkemy.ong.ports.input.rs.response.UserResponse;
import com.alkemy.ong.ports.input.rs.response.UserResponseList;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Validated
public interface UserApi {

    ResponseEntity<UserResponseList> getUsers(Optional<Integer> page, Optional<Integer> size);

    ResponseEntity<UserResponse> updateUser(@NotNull @PathVariable("id") Long id,
                                            @RequestBody UpdateUserRequest updateUserRequest);
}
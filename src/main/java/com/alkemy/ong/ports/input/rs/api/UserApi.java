package com.alkemy.ong.ports.input.rs.api;

import com.alkemy.ong.ports.input.rs.request.UpdateUserRequest;
import com.alkemy.ong.ports.input.rs.response.UserResponse;
import com.alkemy.ong.ports.input.rs.response.UserResponseList;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@SecurityRequirement(name = "bearerAuth")
@Validated
public interface UserApi {

    ResponseEntity<UserResponseList> getUsers(Optional<Integer> page, Optional<Integer> size);

    ResponseEntity<UserResponse> updateUser(@NotNull @PathVariable("id") Long id,
                                            @Valid @RequestBody UpdateUserRequest updateUserRequest);

    ResponseEntity<Void> deleteUser(@NotNull Long id);

}


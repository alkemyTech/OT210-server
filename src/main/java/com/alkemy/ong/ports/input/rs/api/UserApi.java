package com.alkemy.ong.ports.input.rs.api;

import com.alkemy.ong.common.exception.error.ErrorDetails;
import com.alkemy.ong.domain.model.User;
import com.alkemy.ong.ports.input.rs.request.UpdateUserRequest;
import com.alkemy.ong.ports.input.rs.response.AlkymerResponseList;
import com.alkemy.ong.ports.input.rs.response.UserResponse;
import com.alkemy.ong.ports.input.rs.response.UserResponseList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@SecurityRequirement(name = "bearerAuth")
@Validated
public interface UserApi {

    @Operation(summary = "Get User List", description = "Get User List", responses = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AlkymerResponseList.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorDetails.class),
                            examples = @ExampleObject(value = "{\"code\":\"BAD_CREDENTIALS\",\"detail\":\"The server cannot return a response due to invalid credentials.\"}"))}),
    })
    ResponseEntity<UserResponseList> getUsers(Optional<Integer> page, Optional<Integer> size);

    @Operation(summary = "update User", description = "update User", responses = {
            @ApiResponse(responseCode = "204", description = "No Content"),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ErrorDetails.class)),
                            examples = @ExampleObject(value = "[{\"code\":\"INVALID_FIELD_VALUE\",\"detail\":\"must not be blank\",\"field\":\"name\",\"location\":\"BODY\"}]"))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorDetails.class),
                            examples = @ExampleObject(value = "{\"code\":\"BAD_CREDENTIALS\",\"detail\":\"The server cannot return a response due to invalid credentials.\"}"))}),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorDetails.class),
                            examples = @ExampleObject(value = "{\"code\":\"ROLE_INVALID\",\"detail\":\"The user does not have access to the current resource\"}"))}),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorDetails.class),
                            examples = @ExampleObject(value = "{\"code\":\"RESOURCE_NOT_FOUND\",\"detail\":\"The resource with id 99 is not found\"}"))}),
    })
    @Parameter(name = "user", hidden = true)
    ResponseEntity<UserResponse> updateUser(@NotNull @PathVariable("id") Long id,
                                            @Valid @RequestBody UpdateUserRequest updateUserRequest,
                                            @AuthenticationPrincipal User user);

    @Operation(summary = "Delete  User", description = "Delete  User", responses = {
            @ApiResponse(responseCode = "204", description = "No Content"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorDetails.class),
                            examples = @ExampleObject(value = "{\"code\":\"BAD_CREDENTIALS\",\"detail\":\"The server cannot return a response due to invalid credentials.\"}"))}),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorDetails.class),
                            examples = @ExampleObject(value = "{\"code\":\"ROLE_INVALID\",\"detail\":\"The user does not have access to the current resource\"}"))}),
    })
    ResponseEntity<Void> deleteUser(@NotNull Long id);

}


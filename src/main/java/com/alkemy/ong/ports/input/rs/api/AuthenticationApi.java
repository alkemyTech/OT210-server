package com.alkemy.ong.ports.input.rs.api;

import com.alkemy.ong.common.exception.error.ErrorDetails;
import com.alkemy.ong.domain.model.User;
import com.alkemy.ong.ports.input.rs.request.AuthenticationRequest;
import com.alkemy.ong.ports.input.rs.request.CreateUserRequest;
import com.alkemy.ong.ports.input.rs.response.AuthenticationResponse;
import com.alkemy.ong.ports.input.rs.response.UserAndAuthenticationResponse;
import com.alkemy.ong.ports.input.rs.response.UserResponse;
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
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;


@Validated
public interface AuthenticationApi {

    @Operation(summary = "Get User Information", description = "Get User Information", responses ={
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorDetails.class),
                            examples = @ExampleObject(value = "{\"code\":\"BAD_CREDENTIALS\",\"detail\":\"The server cannot return a response due to invalid credentials.\"}"))})
    })
    @Parameter(name = "user", hidden = true)
    @SecurityRequirement(name = "bearerAuth")
    ResponseEntity<UserResponse> getUserInformation(@AuthenticationPrincipal User user);

    @Operation(summary = "User Login", description ="User Login", responses = {
            @ApiResponse(responseCode = "200" , description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AuthenticationResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ErrorDetails.class)),
                            examples = @ExampleObject(value = "[{\"code\":\"INVALID_FIELD_VALUE\",\"detail\":\"must not be blank\",\"field\":\"username\",\"location\":\"BODY\"}]"))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorDetails.class),
                            examples = @ExampleObject(value = "{\"code\":\"BAD_CREDENTIALS\",\"detail\":\"The server cannot return a response due to invalid credentials.\"}"))})
    })
    ResponseEntity<?> login(@Valid @RequestBody AuthenticationRequest request);

    @Operation(summary = "Create User", description = "Create User", responses = {
            @ApiResponse(responseCode = "201" , description = "Created",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserResponse.class))}),
            @ApiResponse(responseCode = "409", description = "Conflict",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ErrorDetails.class)),
                            examples = @ExampleObject(value ="{\"code\":\"RESOURCE_ALREADY_EXISTS\",\"detail\":\"There is already an account with that email address: admin1@somosmas.org.\"}" ))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ErrorDetails.class)),
                            examples = @ExampleObject(value = "[{\"code\":\"INVALID_FIELD_VALUE\",\"detail\":\"el tamaño debe estar entre 8 y 16\",\"field\":\"password\",\"location\":\"BODY\"}]"))}),

    })
    ResponseEntity<UserAndAuthenticationResponse> registerNewUser(@Valid @RequestBody CreateUserRequest userRequest);


}

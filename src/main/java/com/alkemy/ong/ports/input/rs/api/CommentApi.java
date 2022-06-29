package com.alkemy.ong.ports.input.rs.api;

import com.alkemy.ong.common.exception.error.ErrorDetails;
import com.alkemy.ong.domain.model.User;
import com.alkemy.ong.ports.input.rs.request.CreateCommentRequest;
import com.alkemy.ong.ports.input.rs.request.UpdateCommentRequest;
import com.alkemy.ong.ports.input.rs.response.CommentResponse;
import com.alkemy.ong.ports.input.rs.response.CommentResponseList;
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

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Validated
@SecurityRequirement(name = "bearerAuth")
public interface CommentApi {

    @Operation(summary = "Get Comments List", description = "Get Comments List", responses = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CommentResponseList.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorDetails.class),
                            examples = @ExampleObject(value = "{\"code\":\"BAD_CREDENTIALS\",\"detail\":\"The server cannot return a response due to invalid credentials.\"}"))}),
    })
    ResponseEntity<CommentResponseList> getComments(Optional<Integer> page, Optional<Integer> size);

    @Operation(summary = "Create new Comment", description = "Create new Comment",
            responses = {@ApiResponse(responseCode = "201", description = "Created"),
                    @ApiResponse(responseCode = "400", description = "Bad Request",
                            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = ErrorDetails.class)),
                                    examples = @ExampleObject(value = "[{\"code\":\"INVALID_FIELD_VALUE\",\"detail\":\"must not be blank\"," +
                                            " \"field\":\"name\",\"location\":\"BODY\"}]"))}),
                    @ApiResponse(responseCode = "401", description = "Unauthorized",
                            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = ErrorDetails.class)),
                                    examples = @ExampleObject(value = "[{\"code\":\"BAD_CREDENTIALS\"," +
                                            "\"detail\":\"The server cannot return a response due to invalid credentials.\"}]"))}),
                    @ApiResponse(responseCode = "403", description = "Forbidden",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = ErrorDetails.class)),
                                    examples = @ExampleObject(value = "[{\"code\":\"ROLE_INVALID\"," +
                                            "\"detail\":\"The user does not have access to the current resource\"}]")))
            })
    ResponseEntity<Void> createComment(@Valid CreateCommentRequest createCommentRequest,
                                       @AuthenticationPrincipal User user);


    @Operation(summary = "Update Comment", description = "Update Comment", responses = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = CommentResponse.class)))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ErrorDetails.class)),
                            examples = @ExampleObject(value = "[{\"code\":\"INVALID_FIELD_VALUE\"," +
                                    "\"detail\":\"must not be blank\",\"field\":\"name\",\"location\":\"BODY\"}]"))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorDetails.class),
                            examples = @ExampleObject(value = "{\"code\":\"BAD_CREDENTIALS\"," +
                                    "\"detail\":\"The server cannot return a response due to invalid credentials.\"}"))}),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorDetails.class),
                            examples = @ExampleObject(value = "{\"code\":\"ROLE_INVALID\"," +
                                    "\"detail\":\"The user does not have access to the current resource\"}"))}),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorDetails.class),
                            examples = @ExampleObject(value = "{\"code\":\"RESOURCE_NOT_FOUND\"," +
                                    "\"detail\":\"The resource with id 99 is not found\"}"))}),
    })
    @Parameter(name = "user", hidden = true)
    void updateComment(@NotNull Long id, @Valid UpdateCommentRequest commentRequest, @AuthenticationPrincipal User user);

    @Operation(summary = "Delete Comment", description = "Delete Comment", responses = {
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
    @Parameter(name = "user", hidden = true)
    ResponseEntity<Void> deleteComment(@NotNull Long id, @AuthenticationPrincipal User user);
}


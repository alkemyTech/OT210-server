package com.alkemy.ong.ports.input.rs.api;

import com.alkemy.ong.common.exception.error.ErrorDetails;
import com.alkemy.ong.ports.input.rs.request.CreateTestimonialRequest;
import com.alkemy.ong.ports.input.rs.response.TestimonialResponseList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import com.alkemy.ong.ports.input.rs.request.TestimonialRequest;
import com.alkemy.ong.ports.input.rs.response.TestimonialResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Optional;

@SecurityRequirement(name = "bearerAuth")
@Validated
public interface TestimonialApi {

    @Operation(summary = "Create new Testimonial", description = "Create new Testimonial",
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
                         "\"detail\":\"The user does not have access to the current resource\"}]")))})
    ResponseEntity<Void> createTestimonial(@Valid CreateTestimonialRequest createTestimonialRequest);

    @Operation(summary = "Update Testimonial", description = "Update Testimonial", responses = {
            @ApiResponse(responseCode = "204", description = "No Content"),
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
    ResponseEntity<TestimonialResponse> updateTestimonial( Long id, @Valid TestimonialRequest testimonialRequest);

    @Operation(summary = "Delete Testimonial", description = "Delete Testimonial", responses = {
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
    ResponseEntity<Void> deleteTestimonial(Long id);

    @Operation(summary = "Get Testimonial List", description = "Get Testimonial List", responses = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TestimonialResponseList.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorDetails.class),
                            examples = @ExampleObject(value = "{\"code\":\"BAD_CREDENTIALS\"," +
                                    "\"detail\":\"The server cannot return a response due to invalid credentials.\"}"))}),
    })
    ResponseEntity<TestimonialResponseList> getTestimonials(Optional<Integer> page, Optional<Integer> size);
}

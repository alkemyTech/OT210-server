package com.alkemy.ong.ports.input.rs.api;

import com.alkemy.ong.ports.input.rs.request.CreateTestimonialRequest;
import com.alkemy.ong.ports.input.rs.response.TestimonialResponseList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
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

    ResponseEntity<Void> createTestimonial(@Valid CreateTestimonialRequest createTestimonialRequest);

    ResponseEntity<TestimonialResponse> updateTestimonial( Long id, @Valid TestimonialRequest testimonialRequest);

    ResponseEntity<Void> deleteTestimonial(Long id);

    @Operation(summary = "Get Testimonial List", description = "Get Testimonial List", responses = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TestimonialResponseList.class))})})
    ResponseEntity<TestimonialResponseList> getTestimonials(Optional<Integer> page, Optional<Integer> size);
}

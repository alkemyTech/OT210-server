package com.alkemy.ong.ports.input.rs.api;

import com.alkemy.ong.ports.input.rs.request.CreateTestimonialRequest;
import com.alkemy.ong.ports.input.rs.request.TestimonialRequest;
import com.alkemy.ong.ports.input.rs.response.TestimonialResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@SecurityRequirement(name = "bearerAuth")
@Validated
public interface TestimonialApi {

    ResponseEntity<Void> createTestimonial(@Valid CreateTestimonialRequest createTestimonialRequest);

    ResponseEntity<TestimonialResponse> updateTestimonial( Long id, @Valid TestimonialRequest testimonialRequest);

    ResponseEntity<Void> deleteTestimonial(Long id);

}

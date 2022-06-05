package com.alkemy.ong.ports.input.rs.api;

import com.alkemy.ong.domain.model.Testimonial;
import com.alkemy.ong.ports.input.rs.request.CreateTestimonialRequest;
import com.alkemy.ong.ports.input.rs.request.TestimonialRequest;
import com.alkemy.ong.ports.input.rs.response.TestimonialResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
public interface TestimonialApi {

    ResponseEntity<Void> createTestimonial(@Valid CreateTestimonialRequest createTestimonialRequest);
    ResponseEntity<TestimonialResponse> updateTestimonial(@Valid Long id, TestimonialRequest testimonialRequest);
}

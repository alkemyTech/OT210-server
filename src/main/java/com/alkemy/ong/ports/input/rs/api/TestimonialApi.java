package com.alkemy.ong.ports.input.rs.api;

import com.alkemy.ong.ports.input.rs.request.CreateTestimonialRequest;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;

public interface TestimonialApi {

    ResponseEntity<Void> createTestimonial(@Valid CreateTestimonialRequest createTestimonialRequest);
}

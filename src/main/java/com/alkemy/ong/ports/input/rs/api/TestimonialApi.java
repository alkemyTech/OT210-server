package com.alkemy.ong.ports.input.rs.api;

import com.alkemy.ong.ports.input.rs.request.CreateTestimonialRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
public interface TestimonialApi {

    ResponseEntity<Void> createTestimonial(@Valid CreateTestimonialRequest createTestimonialRequest);
    ResponseEntity<Void> deleteTestimonial(Long id);
}

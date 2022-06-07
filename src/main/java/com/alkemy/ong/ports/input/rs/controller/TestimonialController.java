package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.domain.model.Testimonial;
import com.alkemy.ong.domain.usecase.TestimonialService;
import com.alkemy.ong.ports.input.rs.api.TestimonialApi;
import com.alkemy.ong.ports.input.rs.mapper.TestimonialControllerMapper;
import com.alkemy.ong.ports.input.rs.request.CreateTestimonialRequest;
import com.alkemy.ong.ports.input.rs.request.TestimonialRequest;
import com.alkemy.ong.ports.input.rs.response.TestimonialResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

import static com.alkemy.ong.ports.input.rs.api.ApiConstants.TESTIMONIALS_URI;

@RestController
@RequestMapping(TESTIMONIALS_URI)
@RequiredArgsConstructor
public class TestimonialController implements TestimonialApi {

    private final TestimonialControllerMapper mapper;

    private final TestimonialService service;

    @Override
    @PostMapping
    public ResponseEntity<Void> createTestimonial(@Valid @RequestBody CreateTestimonialRequest createTestimonialRequest) {

        Testimonial testimonial = mapper.createTestimonialRequestToTestimonial(createTestimonialRequest);

        final long id = service.createEntity(testimonial);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(id)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<TestimonialResponse> updateTestimonial(@PathVariable  Long id, @RequestBody @Valid TestimonialRequest testimonialRequest){

        Testimonial testimonial = mapper.testimonialRequestToTestimonial(testimonialRequest);
        Testimonial testimonial1 = service.updateIfExists(id ,testimonial);
        TestimonialResponse testimonialResponse = mapper.testimonialToTestimonialResopnse(testimonial1);

        return new ResponseEntity<>(testimonialResponse, HttpStatus.OK);
    }

     @Override
    @DeleteMapping("/a/{id}")
    public ResponseEntity<Void> deleteTestimonial(@PathVariable Long id) {
        service.deleteTestimonial(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}

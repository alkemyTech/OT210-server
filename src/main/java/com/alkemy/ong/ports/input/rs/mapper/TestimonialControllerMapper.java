package com.alkemy.ong.ports.input.rs.mapper;

import com.alkemy.ong.domain.model.Testimonial;
import com.alkemy.ong.ports.input.rs.request.CreateTestimonialRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Optional;

@Mapper
public interface TestimonialControllerMapper extends CommonMapper {

    @Mapping(target = "name", source = "name")
    @Mapping(target = "image", source = "image")
    @Mapping(target = "content",source = "content")
    CreateTestimonialRequest testimonialToCreateTestimonialRequest(Optional<Testimonial> testimonial);

    Testimonial createTestimonialRequestToTestimonial(CreateTestimonialRequest create);


}

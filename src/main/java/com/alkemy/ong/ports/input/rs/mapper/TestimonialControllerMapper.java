package com.alkemy.ong.ports.input.rs.mapper;

import com.alkemy.ong.domain.model.Testimonial;
import com.alkemy.ong.ports.input.rs.request.CreateTestimonialRequest;
import com.alkemy.ong.ports.input.rs.request.TestimonialRequest;
import com.alkemy.ong.ports.input.rs.response.TestimonialResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;



@Mapper
public interface TestimonialControllerMapper extends CommonMapper {

    @Mapping(target = "name", source = "name")
    @Mapping(target = "image", source = "image")
    @Mapping(target = "content",source = "content")
    Testimonial testimonialRequestToTestimonial(TestimonialRequest testimonialRequest);
    TestimonialResponse testimonialToTestimonialResopnse(Testimonial testimonial);

    Testimonial createTestimonialRequestToTestimonial(CreateTestimonialRequest create);


}

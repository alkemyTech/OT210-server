package com.alkemy.ong.ports.input.rs.mapper;

import com.alkemy.ong.domain.model.Testimonial;
import com.alkemy.ong.ports.input.rs.request.CreateTestimonialRequest;
import com.alkemy.ong.ports.input.rs.response.TestimonialResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface TestimonialControllerMapper extends CommonMapper {

    Testimonial createTestimonialRequestToTestimonial(CreateTestimonialRequest create);

    List<TestimonialResponse> testimonialListToTestimonialResponseList(List<Testimonial> list);
}

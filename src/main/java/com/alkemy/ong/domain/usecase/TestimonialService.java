package com.alkemy.ong.domain.usecase;

import com.alkemy.ong.domain.model.Testimonial;

public interface TestimonialService {

    Long createEntity(Testimonial testimonial);

    Testimonial updateIfExists(Long id, Testimonial testimonial);
}

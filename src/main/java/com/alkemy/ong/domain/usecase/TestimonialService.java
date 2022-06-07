package com.alkemy.ong.domain.usecase;

import com.alkemy.ong.domain.model.Testimonial;
import com.alkemy.ong.domain.model.TestimonialList;
import org.springframework.data.domain.PageRequest;

public interface TestimonialService {

    Long createEntity(Testimonial testimonial);
    void deleteTestimonial(Long id);
    TestimonialList getList(PageRequest pageRequest);
}

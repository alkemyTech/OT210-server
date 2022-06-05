package com.alkemy.ong.domain.usecase.impl;

import com.alkemy.ong.domain.model.Testimonial;
import com.alkemy.ong.domain.repository.TestimonialRepository;
import com.alkemy.ong.domain.usecase.TestimonialService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class TestimonialServiceImpl implements TestimonialService {

    private final TestimonialRepository testimonialJpaRepository;
    @Override
    @Transactional
    public Long createEntity(Testimonial testimonial) {
        return testimonialJpaRepository.save(testimonial).getId();
    }

    @Override
    @Transactional
    public void deleteTestimonial(Long id) {
        testimonialJpaRepository.deleteById(id);
    }
}

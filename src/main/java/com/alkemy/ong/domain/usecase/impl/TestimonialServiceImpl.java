package com.alkemy.ong.domain.usecase.impl;

import com.alkemy.ong.domain.model.Testimonial;
import com.alkemy.ong.domain.model.TestimonialList;
import com.alkemy.ong.domain.repository.TestimonialRepository;
import com.alkemy.ong.domain.usecase.TestimonialService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
        testimonialJpaRepository.findById(id).ifPresent(testimonialJpaRepository::delete);
    }

    @Override
    @Transactional
    public TestimonialList getList(PageRequest pageRequest) {
        Page<Testimonial> page = testimonialJpaRepository.findAll(pageRequest);
        return new TestimonialList(page.getContent(), pageRequest, page.getTotalElements());
    }
}

package com.alkemy.ong.domain.usecase.impl;

import com.alkemy.ong.common.exception.NotFoundException;
import com.alkemy.ong.domain.model.Testimonial;
import com.alkemy.ong.domain.repository.TestimonialRepository;
import com.alkemy.ong.domain.usecase.TestimonialService;
import com.alkemy.ong.ports.input.rs.request.CreateTestimonialRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


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
    public Testimonial updateIfExists(Long id, Testimonial testimonial) {

       return testimonialJpaRepository.findById(id)
                .map(testimonialJpa -> {
                    Optional.ofNullable(testimonial.getContent()).ifPresent(testimonialJpa::setContent);
                    Optional.ofNullable(testimonial.getImage()).ifPresent(testimonialJpa::setImage);
                    Optional.ofNullable(testimonial.getName()).ifPresent(testimonialJpa::setName);

                    return testimonialJpaRepository.save(testimonialJpa);
                } ).orElseThrow(() -> new NotFoundException(id));


    }
}

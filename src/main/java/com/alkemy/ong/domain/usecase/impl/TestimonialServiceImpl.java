package com.alkemy.ong.domain.usecase.impl;

import com.alkemy.ong.common.exception.NotFoundException;
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


    @Transactional
    @Override
    public Testimonial updateIfExists(Long id, Testimonial testimonial) {

        Testimonial testimonial1 = testimonialJpaRepository.findById(id)
                .map(testimonialJpa -> {

                    testimonialJpa.setImage(testimonial.getImage());
                    testimonialJpa.setContent(testimonial.getContent());
                    testimonialJpa.setName(testimonial.getName());

                    return testimonialJpaRepository.save(testimonialJpa);
                }).orElseThrow(() -> new NotFoundException(id));


        return testimonial1;

    }

    @Override
    @Transactional
    public void deleteTestimonial(Long id) {
        testimonialJpaRepository.findById(id).ifPresent(testimonialJpaRepository::delete);

    }
}

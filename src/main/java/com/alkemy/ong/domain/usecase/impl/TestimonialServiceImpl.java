package com.alkemy.ong.domain.usecase.impl;

import com.alkemy.ong.domain.model.Testimonial;
import com.alkemy.ong.domain.repository.TestimonialRepository;
import com.alkemy.ong.domain.usecase.TestimonialService;
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
    public Optional<Testimonial> updateIfExists(Long id, Testimonial testimonial) {

        Optional<Testimonial> testimonial1 = testimonialJpaRepository.findById(id)
                .map(testimonialJpa ->{

                    testimonialJpa.setImage(testimonial.getImage());
                    testimonialJpa.setContent(testimonial.getContent());
                    testimonialJpa.setName(testimonial.getName());

                    return testimonialJpaRepository.save(testimonialJpa);
                } );


        return testimonial1;
    }
}

package com.alkemy.ong.domain.usecase.impl;

import com.alkemy.ong.common.exception.NotFoundException;
import com.alkemy.ong.domain.model.Slide;
import com.alkemy.ong.domain.repository.SlideRepository;
import com.alkemy.ong.domain.usecase.SlideService;

import com.alkemy.ong.ports.input.rs.request.SlideRequest;
import com.alkemy.ong.ports.output.s3.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class SlideServiceImpl implements SlideService {

    private final SlideRepository slideRepository;
    private final S3Service s3Service;

    @Override
    @Transactional(readOnly = true)
    public Slide getByIdIfExist(Long id) {
        return slideRepository.findById(id)
                .orElseThrow(() ->new NotFoundException(id));
    }

    @Override
    @Transactional(readOnly = true)
    public void updateSlideIfExist(Long id , Slide slide ,String fileName ) {

        slideRepository.findById(id).map(slideJpa -> {
            slideJpa.setImageUrl(s3Service.uploadFile(slide.getImageUrl(),fileName));
            slideJpa.setOrder(slide.getOrder());
            slideJpa.setOrganization(slide.getOrganization());

            return slideRepository.save(slideJpa);
        }).orElseThrow(() -> new NotFoundException(id));

    }


}

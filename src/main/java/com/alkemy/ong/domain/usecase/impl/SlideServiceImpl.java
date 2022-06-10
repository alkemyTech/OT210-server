package com.alkemy.ong.domain.usecase.impl;

import com.alkemy.ong.common.exception.NotFoundException;
import com.alkemy.ong.domain.model.Organization;
import com.alkemy.ong.domain.model.Slide;
import com.alkemy.ong.domain.repository.OrganizationRepository;
import com.alkemy.ong.domain.repository.SlideRepository;
import com.alkemy.ong.domain.usecase.SlideService;
import com.alkemy.ong.ports.output.s3.S3ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SlideServiceImpl implements SlideService {

    private final OrganizationRepository organizationRepository;
    private final S3ServiceImpl s3Service;
    private final SlideRepository slideRepository;



    @Override
    @Transactional
    public Long createSlide(Slide slide , String fileName) {

        Slide slideEntity = new Slide();
        Optional<Organization> organization = Optional.ofNullable((organizationRepository.findById(slide.getOrganization().getId())
                .orElseThrow(() -> {
                    return new NotFoundException(slide.getOrganization().getId());
                })));

        slideEntity.setOrganization(organization.get());

        Integer slideListMax = slideRepository.getMaxOrder();
        if (slide.getOrder() == null) {
            slideEntity.setOrder(1 + slideListMax);
        } else if (slide.getOrder() != slideListMax || slide.getOrder() != 0) {
            slideEntity.setOrder(slide.getOrder());
        } else if (slideListMax == slide.getOrder()) {
            slideEntity.setOrder(slideListMax + 1);
        }

        String decodedImage = (s3Service.uploadFile(slide.getImageUrl(),fileName));
        slideEntity.setImageUrl(decodedImage);
        slideEntity.setText(slide.getText());

        return slideRepository.save(slideEntity).getId();
    }
}

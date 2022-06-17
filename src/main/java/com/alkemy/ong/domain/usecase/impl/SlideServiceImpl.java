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


@Service
@RequiredArgsConstructor
public class SlideServiceImpl implements SlideService {

    private final OrganizationRepository organizationRepository;
    private final S3ServiceImpl s3Service;
    private final SlideRepository slideRepository;

    private final static String FILE_NAME = "Slide_image";


    @Override
    @Transactional
    public Long createSlide(String imgBase64, String text, Integer order, Long organizationId) {

        Slide slideEntity = new Slide();

        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new NotFoundException(organizationId));
        slideEntity.setOrganization(organization);

        Integer maxOrder = slideRepository.getMaxOrder().orElse(0);
        if (order == null || order <= maxOrder) {
            slideEntity.setOrder(++maxOrder);
        } else {
            slideEntity.setOrder(order);
        }

        String url = s3Service.uploadFile(imgBase64, FILE_NAME);
        slideEntity.setImageUrl(url);

        slideEntity.setText(text);

        return slideRepository.save(slideEntity).getId();
    }

    @Override
    @Transactional(readOnly = true)
    public Slide getByIdIfExist(Long id) {
        return slideRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }

    @Override
    @Transactional
    public void deleteSlideByIdIfExist(Long id) {
        slideRepository.findById(id).ifPresent(slideRepository::delete);
    }

}

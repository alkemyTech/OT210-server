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

    private final String FILE_NAME ="Slide_image";



    @Override
    @Transactional
    public void createSlide(String imgBase64 , String text , Integer order, Long organizationId) {

        Slide slideEntity = new Slide();

        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new NotFoundException(organizationId));
        slideEntity.setOrganization(organization);

        Integer slideListMax = slideRepository.getMaxOrder();
       if (order == null ||order <= slideListMax){
           slideEntity.setOrder(slideListMax + 1);
       } else {
           slideEntity.setOrder(order);
       }

        String decodedImage = (s3Service.uploadFile(imgBase64, FILE_NAME));
        slideEntity.setImageUrl(decodedImage);
        slideEntity.setText(text);

         slideRepository.save(slideEntity);
    }
}

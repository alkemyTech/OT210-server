package com.alkemy.ong.domain.usecase.impl;

import com.alkemy.ong.domain.model.Slide;
import com.alkemy.ong.domain.model.SlideList;
import com.alkemy.ong.domain.repository.SlideRepository;
import com.alkemy.ong.domain.usecase.SlideService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.alkemy.ong.common.exception.NotFoundException;

@Service
@RequiredArgsConstructor
public class SlideServiceImpl implements SlideService {

    private final SlideRepository slideRepository;

    @Override
    @Transactional(readOnly = true)
    public Slide getByIdIfExist(Long id) {
        return slideRepository.findById(id)
                .orElseThrow(() ->new NotFoundException(id));
    }

    @Override
    @Transactional
    public void deleteSlideByIdIfExist(Long id) {
        slideRepository.findById(id).ifPresent(slideRepository::delete);
    }
  
   @Override
    @Transactional(readOnly = true)
    public SlideList getList(PageRequest pageRequest) {
        Page<Slide> page = slideRepository.findAll(pageRequest);
        return new SlideList(page.getContent(), pageRequest, page.getTotalElements());
    }

}

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

@Service
@RequiredArgsConstructor
public class SlideServiceImpl implements SlideService {

    private final SlideRepository slideJpaRepository;

    @Override
    @Transactional(readOnly = true)
    public SlideList getList(PageRequest pageRequest) {
        Page<Slide> page = slideJpaRepository.findAll(pageRequest);
        return new SlideList(page.getContent(), pageRequest, page.getTotalElements());
    }
}

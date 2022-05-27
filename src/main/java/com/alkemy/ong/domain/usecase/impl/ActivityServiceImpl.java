package com.alkemy.ong.domain.usecase.impl;

import com.alkemy.ong.domain.model.Activity;
import com.alkemy.ong.domain.repository.ActivityRepository;
import com.alkemy.ong.domain.usecase.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityJpaRepository;

    @Override
    @Transactional
    public Long createEntity(Activity entity) {
        return activityJpaRepository.save(entity).getId();
    }
}

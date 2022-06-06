package com.alkemy.ong.domain.usecase.impl;

import com.alkemy.ong.common.exception.NotFoundException;
import com.alkemy.ong.domain.model.Activity;
import com.alkemy.ong.domain.repository.ActivityRepository;
import com.alkemy.ong.domain.usecase.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityJpaRepository;

    @Override
    @Transactional
    public Long createEntity(Activity entity) {
        return activityJpaRepository.save(entity).getId();
    }

    @Override
    @Transactional
    public void updateEntityIfExists(Long id, Activity activity) {
        activityJpaRepository.findById(id)
                .map(activityJpa -> {
                    Optional.ofNullable(activity.getName()).ifPresent(activityJpa::setName);
                    Optional.ofNullable(activity.getContent()).ifPresent(activityJpa::setContent);
                    Optional.ofNullable(activity.getImage()).ifPresent(activityJpa::setImage);

                    return activityJpaRepository.save(activityJpa);
                }).orElseThrow(() -> new NotFoundException(id));
    }
}

package com.alkemy.ong.domain.usecase;

import com.alkemy.ong.domain.model.Activity;

public interface ActivityService {
    Long createEntity(Activity entity);

    void updateEntityIfExists(Long id, Activity activity);
}

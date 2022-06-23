package com.alkemy.ong.domain.usecase;

import com.alkemy.ong.domain.model.Organization;
import com.alkemy.ong.domain.model.Slide;

import java.util.List;

public interface OrganizationService {

    Organization getByIdIfExists(Long id);

    void updateEntityIfExists(Long id, Organization organization);

    List<Slide> getSlides(Long id);
}

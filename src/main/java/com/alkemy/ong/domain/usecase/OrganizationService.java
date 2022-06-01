package com.alkemy.ong.domain.usecase;

import com.alkemy.ong.domain.model.Organization;
import org.springframework.data.domain.PageRequest;

public interface OrganizationService {

    Organization getByIdIfExists(Long id);

    void updateEntityIfExists(Long id, Organization organization);
}

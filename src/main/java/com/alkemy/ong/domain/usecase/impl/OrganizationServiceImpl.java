package com.alkemy.ong.domain.usecase.impl;

import com.alkemy.ong.common.exception.NotFoundException;
import com.alkemy.ong.domain.model.Organization;
import com.alkemy.ong.domain.repository.OrganizationRepository;
import com.alkemy.ong.domain.usecase.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;


    @Override
    @Transactional(readOnly = true)
    public Organization getByIdIfExists(Long id) {
        return organizationRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    @Override
    @Transactional
    public void updateEntityIfExists(Long id, Organization organization) {
        organizationRepository.findById(id)
                .map(organizationJpa -> {
                    organizationJpa.setName(organization.getName());
                    organizationJpa.setImage(organization.getImage());
                    organizationJpa.setAddress(organization.getAddress());
                    organizationJpa.setPhone(organization.getPhone());
                    organizationJpa.setWelcomeText(organization.getWelcomeText());
                    organizationJpa.setAboutUsText(organization.getAboutUsText());
                    return organizationRepository.save(organizationJpa);
                }).orElseThrow(() -> new NotFoundException(id));
    }
}

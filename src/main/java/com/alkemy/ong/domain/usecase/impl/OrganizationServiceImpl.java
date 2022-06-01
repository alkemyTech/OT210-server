package com.alkemy.ong.domain.usecase.impl;

import com.alkemy.ong.common.exception.NotFoundException;
import com.alkemy.ong.domain.model.Organization;
import com.alkemy.ong.domain.repository.OrganizationRepository;
import com.alkemy.ong.domain.usecase.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
                    Optional.ofNullable(organization.getName()).ifPresent(organizationJpa::setName);
                    Optional.ofNullable(organization.getImage()).ifPresent(organizationJpa::setImage);
                    Optional.ofNullable(organization.getAddress()).ifPresent(organizationJpa::setAddress);
                    Optional.ofNullable(organization.getPhone()).ifPresent(organizationJpa::setPhone);
                    Optional.ofNullable(organization.getWelcomeText()).ifPresent(organizationJpa::setWelcomeText);
                    Optional.ofNullable(organization.getAboutUsText()).ifPresent(organizationJpa::setAboutUsText);
                    return organizationRepository.save(organizationJpa);
                }).orElseThrow(() -> new NotFoundException(id));
    }
}

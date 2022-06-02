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
}

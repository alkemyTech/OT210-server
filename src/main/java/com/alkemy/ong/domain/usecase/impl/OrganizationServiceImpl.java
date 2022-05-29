package com.alkemy.ong.domain.usecase.impl;

import com.alkemy.ong.domain.model.Organization;
import com.alkemy.ong.domain.model.OrganizationList;
import com.alkemy.ong.domain.repository.OrganizationRepository;
import com.alkemy.ong.domain.usecase.OrganizationService;
import com.alkemy.ong.ports.input.rs.response.OrganizationResponse;
import com.alkemy.ong.ports.input.rs.response.OrganizationResponseList;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;

    @Override
    @Transactional
    public OrganizationList getList(PageRequest pageRequest) {
        Page<Organization> page = organizationRepository.findAll(pageRequest);
        return  new OrganizationList(page.getContent(), pageRequest, page.getTotalElements());
    }
}

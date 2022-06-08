package com.alkemy.ong.domain.usecase.impl;

import com.alkemy.ong.domain.model.Contact;
import com.alkemy.ong.domain.repository.ContactRepository;
import com.alkemy.ong.domain.usecase.ContactService;
import com.alkemy.ong.domain.usecase.OrganizationService;
import com.alkemy.ong.ports.output.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactJpaRepository;

    private final OrganizationService organizationService;
    private final EmailService emailService;

    @Value("${default.organization.id}")
    private Long defaultOrganizationId;

    @Override
    @Transactional
    public Long createEntity(Contact entity) {

        emailService.sendContactEmail(entity.getEmail(), organizationService.getByIdIfExists(defaultOrganizationId));

        return contactJpaRepository.save(entity).getId();
    }
}

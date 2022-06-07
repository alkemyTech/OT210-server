package com.alkemy.ong.domain.usecase.impl;

import com.alkemy.ong.domain.model.Contact;
import com.alkemy.ong.domain.repository.ContactRepository;
import com.alkemy.ong.domain.usecase.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactJpaRepository;

    @Override
    @Transactional
    public Long createEntity(Contact entity) {
        return contactJpaRepository.save(entity).getId();
    }
}

package com.alkemy.ong.domain.usecase;

import com.alkemy.ong.domain.model.Contact;
import com.alkemy.ong.domain.model.ContactList;
import org.springframework.data.domain.PageRequest;

public interface ContactService {

    Long createEntity(Contact entity);
    ContactList getList(PageRequest pageRequest);
}

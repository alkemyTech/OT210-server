package com.alkemy.ong.domain.model;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class ContactList extends PageImpl<Contact> {
    public ContactList(List<Contact> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
}

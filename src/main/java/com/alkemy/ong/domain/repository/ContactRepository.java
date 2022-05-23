package com.alkemy.ong.domain.repository;

import com.alkemy.ong.domain.model.Contact;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

public interface ContactRepository extends PagingAndSortingRepository<Contact, Long> {
}

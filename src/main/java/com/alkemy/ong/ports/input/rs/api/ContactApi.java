package com.alkemy.ong.ports.input.rs.api;

import com.alkemy.ong.ports.input.rs.request.CreateContactRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
public interface ContactApi {

    ResponseEntity<Void> createContact(@Valid CreateContactRequest createContactRequest);
}

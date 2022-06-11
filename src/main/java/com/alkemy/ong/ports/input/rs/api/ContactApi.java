package com.alkemy.ong.ports.input.rs.api;

import com.alkemy.ong.ports.input.rs.request.CreateContactRequest;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@SecurityRequirement(name = "bearerAuth")
@Validated
public interface ContactApi {

    ResponseEntity<Void> createContact(@Valid CreateContactRequest createContactRequest);
}

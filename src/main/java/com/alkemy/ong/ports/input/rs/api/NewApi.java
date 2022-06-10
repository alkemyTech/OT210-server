package com.alkemy.ong.ports.input.rs.api;

import com.alkemy.ong.ports.input.rs.request.CreateNewRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
public interface NewApi {

    ResponseEntity<Void> createNew(@Valid CreateNewRequest createNewRequest);
}

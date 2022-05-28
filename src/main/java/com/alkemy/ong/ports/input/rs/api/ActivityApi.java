package com.alkemy.ong.ports.input.rs.api;

import com.alkemy.ong.ports.input.rs.request.CreateActivityRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
public interface ActivityApi {
    ResponseEntity<Void> createActivity(@Valid CreateActivityRequest createActivityRequest);
}

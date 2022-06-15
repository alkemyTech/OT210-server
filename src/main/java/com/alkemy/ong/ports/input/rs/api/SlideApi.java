package com.alkemy.ong.ports.input.rs.api;


import com.alkemy.ong.ports.input.rs.request.SlideRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
public interface SlideApi {

    ResponseEntity<Void> createSlide(@Valid SlideRequest slideRequest);
}

package com.alkemy.ong.ports.input.rs.api;

import com.alkemy.ong.ports.input.rs.response.SlideResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
public interface SlideApi {

    ResponseEntity<SlideResponse> getById(@NotNull Long id);


}

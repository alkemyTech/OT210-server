package com.alkemy.ong.ports.input.rs.api;

import com.alkemy.ong.ports.input.rs.request.SlideRequest;
import com.alkemy.ong.ports.input.rs.response.SlideResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Validated
public interface SlideApi {

    ResponseEntity<SlideResponse> getById(@NotNull Long id);
    ResponseEntity<Void> deleteSlide(@NotNull Long id);
    ResponseEntity<Void> updateSlideIfExist(Long id , @Valid SlideRequest slideRequest);



}

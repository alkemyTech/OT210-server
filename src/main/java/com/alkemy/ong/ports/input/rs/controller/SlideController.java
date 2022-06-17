package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.domain.model.Slide;
import com.alkemy.ong.domain.usecase.SlideService;
import com.alkemy.ong.ports.input.rs.api.SlideApi;
import com.alkemy.ong.ports.input.rs.mapper.SlideControllerMapper;
import com.alkemy.ong.ports.input.rs.request.SlideRequest;
import com.alkemy.ong.ports.input.rs.response.SlideResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import static com.alkemy.ong.ports.input.rs.api.ApiConstants.SLIDES_URI;


@RequiredArgsConstructor
@RestController
@RequestMapping(SLIDES_URI)
public class SlideController implements SlideApi {

    private final SlideControllerMapper mapper;
    private final SlideService slideService;

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<SlideResponse> getById(@PathVariable @NotNull Long id) {

        Slide slide = slideService.getByIdIfExist(id);
        SlideResponse slideResponse = mapper.slideToSlideResponse(slide);
        return new ResponseEntity<>(slideResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSlide(@PathVariable @NotNull Long id) {

        slideService.deleteSlideByIdIfExist(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    @PostMapping
    public ResponseEntity<Void> createSlide(@RequestBody @Valid SlideRequest slideRequest) {

        slideService.createSlide(slideRequest.getImg()
                , slideRequest.getText(), slideRequest.getOrder(),
                slideRequest.getOrganizationId());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
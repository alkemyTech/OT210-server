package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.domain.model.Slide;
import com.alkemy.ong.domain.usecase.SlideService;
import com.alkemy.ong.ports.input.rs.api.SlideApi;
import com.alkemy.ong.ports.input.rs.request.SlideRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static com.alkemy.ong.ports.input.rs.api.ApiConstants.SLIDES_URI;

@RestController
@RequiredArgsConstructor
@RequestMapping(SLIDES_URI)
public class SlideController implements SlideApi {

    private final SlideService slideService;


    @Override
    @PostMapping
    public ResponseEntity<Void> createSlide(@RequestBody SlideRequest slideRequest, String fileName) {

        Slide slide = new Slide();

        slide.setImageUrl(slideRequest.getImg());
        slide.setOrder(slideRequest.getOrder());
        slide.setText(slideRequest.getText());

        final Long id = slideService.createSlide(slide,fileName);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(id)
                .toUri();

        return ResponseEntity.created(location).build();
    }
}

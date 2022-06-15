package com.alkemy.ong.ports.input.rs.controller;


import com.alkemy.ong.domain.usecase.SlideService;
import com.alkemy.ong.ports.input.rs.api.SlideApi;
import com.alkemy.ong.ports.input.rs.request.SlideRequest;
import com.alkemy.ong.ports.output.s3.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.validation.Valid;


import static com.alkemy.ong.ports.input.rs.api.ApiConstants.SLIDES_URI;

@RestController
@RequiredArgsConstructor
@RequestMapping(SLIDES_URI)
public class SlideController implements SlideApi {

    private final SlideService slideService;
    private final S3Service s3Service;


    @Override
    @PostMapping("/Slides")
    public ResponseEntity<Void> createSlide(@RequestBody @Valid SlideRequest slideRequest) {

        slideService.createSlide(slideRequest.getImg()
                ,slideRequest.getText(),slideRequest.getOrder(),
                slideRequest.getOrganizationId());

        return new ResponseEntity<>(HttpStatus.CREATED);

    }
}

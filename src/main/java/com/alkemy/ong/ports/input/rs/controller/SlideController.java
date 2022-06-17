package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.domain.model.SlideList;
import com.alkemy.ong.domain.usecase.SlideService;
import com.alkemy.ong.ports.input.rs.api.ApiConstants;
import com.alkemy.ong.ports.input.rs.api.SlideApi;
import com.alkemy.ong.ports.input.rs.mapper.SlideControllerMapper;
import com.alkemy.ong.ports.input.rs.request.SlideRequest;
import com.alkemy.ong.ports.input.rs.response.SlideResponse;
import com.alkemy.ong.ports.input.rs.response.SlideResponseList;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.alkemy.ong.domain.model.Slide;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;

import static com.alkemy.ong.ports.input.rs.api.ApiConstants.SLIDES_URI;

@RequiredArgsConstructor
@RestController
@RequestMapping(SLIDES_URI)
public class SlideController implements SlideApi {

    private final SlideControllerMapper mapper;
    private final SlideService slideService;

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<SlideResponse> getById(@PathVariable @NotNull Long id) {

        Slide slide = slideService.getByIdIfExist(id);
        SlideResponse slideResponse = mapper.slideToSlideResponse(slide);
        return new ResponseEntity<>(slideResponse, HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSlide(@PathVariable @NotNull Long id) {
        slideService.deleteSlideByIdIfExist(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    @PostMapping
    public ResponseEntity<Void> createSlide(@RequestBody @Valid SlideRequest slideRequest) {

        final long id = slideService.createSlide(slideRequest.getImg(),
                slideRequest.getText(),
                slideRequest.getOrder(),
                slideRequest.getOrganizationId());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(id)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @Override
    @GetMapping
    public ResponseEntity<SlideResponseList> getSlides(@RequestParam Optional<Integer> page,
                                                       @RequestParam Optional<Integer> size) {

        final int pageNumber = page.filter(p -> p > 0).orElse(ApiConstants.DEFAULT_PAGE);
        final int pageSize = size.filter(s -> s > 0).orElse(ApiConstants.DEFAULT_PAGE_SIZE);

        SlideList list = slideService.getList(PageRequest.of(pageNumber, pageSize));

        SlideResponseList response;
        {
            response = new SlideResponseList();

            List<SlideResponse> content = mapper.slideListToSlideResponseList(list.getContent());
            response.setContent(content);

            final int nextPage = list.getPageable().next().getPageNumber();
            response.setNextUri(ApiConstants.uriByPageAsString.apply(nextPage));

            final int previousPage = list.getPageable().previousOrFirst().getPageNumber();
            response.setPreviousUri(ApiConstants.uriByPageAsString.apply(previousPage));

            response.setTotalPages(list.getTotalPages());
            response.setTotalElements(list.getTotalElements());
        }
        return ResponseEntity.ok().body(response);
    }
  
}

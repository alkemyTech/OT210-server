package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.domain.model.Activity;
import com.alkemy.ong.domain.usecase.ActivityService;
import com.alkemy.ong.ports.input.rs.api.ActivityApi;
import com.alkemy.ong.ports.input.rs.mapper.ActivityControllerMapper;
import com.alkemy.ong.ports.input.rs.request.CreateActivityRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import java.net.URI;

import static com.alkemy.ong.ports.input.rs.api.ApiConstants.ACTIVITIES_URI;

@RestController
@RequestMapping(ACTIVITIES_URI)
@RequiredArgsConstructor
public class ActivityController implements ActivityApi {

    private final ActivityService activityService;
    private final ActivityControllerMapper mapper;

    @Override
    @PostMapping
    public ResponseEntity<Void> createActivity(@Valid @RequestBody CreateActivityRequest createActivityRequest) {

        Activity activity = mapper.createActivityRequestToActivity(createActivityRequest);

        final long id = activityService.createEntity(activity);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(id)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @Override
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateActivity(@NotNull @PathVariable Long id, @Valid @RequestBody CreateActivityRequest createActivityRequest) {
        Activity activity = mapper.createActivityRequestToActivity(createActivityRequest);
        activityService.updateEntityIfExists(id, activity);
    }
}

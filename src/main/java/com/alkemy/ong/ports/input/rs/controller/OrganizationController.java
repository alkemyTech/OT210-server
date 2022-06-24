package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.domain.model.Organization;
import com.alkemy.ong.domain.model.Slide;
import com.alkemy.ong.domain.usecase.OrganizationService;
import com.alkemy.ong.ports.input.rs.api.OrganizationApi;
import com.alkemy.ong.ports.input.rs.mapper.OrganizationControllerMapper;
import com.alkemy.ong.ports.input.rs.request.UpdateOrganizationRequest;
import com.alkemy.ong.ports.input.rs.response.OrganizationResponse;
import com.alkemy.ong.ports.input.rs.response.SlideResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

import static com.alkemy.ong.ports.input.rs.api.ApiConstants.ORGANIZATIONS_URI;

@RestController
@RequestMapping(ORGANIZATIONS_URI)
@RequiredArgsConstructor
public class OrganizationController implements OrganizationApi {

    private final OrganizationService organizationService;
    private final OrganizationControllerMapper mapper;


    @Override
    @GetMapping("/{id}")
    public ResponseEntity<OrganizationResponse> getOrganization(@NotNull @PathVariable Long id) {
        Organization organization = organizationService.getByIdIfExists(id);
        OrganizationResponse organizationResponse = mapper.organizationToOrganizationResponse(organization);
        return ResponseEntity.ok().body(organizationResponse);
    }

    @Override
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateOrganization(@NotNull @PathVariable Long id, @Valid @RequestBody UpdateOrganizationRequest updateOrganizationRequest){
        Organization organization  = mapper.updateOrganizationRequestToOrganization(updateOrganizationRequest);
        organizationService.updateEntityIfExists(id, organization);
    }

    @Override
    @GetMapping("/{id}/slides")
    public List<SlideResponse> getSlides(@NotNull @PathVariable Long id) {
        List<Slide> list = organizationService.getSlides(id);
        return mapper.slideListToSlideResponseList(list);
    }

}

package com.alkemy.ong.ports.input.rs.mapper;

import com.alkemy.ong.domain.model.Organization;
import com.alkemy.ong.domain.model.Slide;
import com.alkemy.ong.ports.input.rs.request.UpdateOrganizationRequest;
import com.alkemy.ong.ports.input.rs.response.OrganizationResponse;
import com.alkemy.ong.ports.input.rs.response.SlideResponse;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper
public interface OrganizationControllerMapper extends CommonMapper{

    OrganizationResponse organizationToOrganizationResponse(Organization organization);

    Organization updateOrganizationRequestToOrganization(UpdateOrganizationRequest updateOrganizationRequest);

    @IterableMapping(qualifiedByName = "slideToSlideResponse")
    List<SlideResponse> slideListToSlideResponseList(List<Slide> slides);

    @Named("slideToSlideResponse")
    SlideResponse slideToSlideResponse(Slide slide);
}

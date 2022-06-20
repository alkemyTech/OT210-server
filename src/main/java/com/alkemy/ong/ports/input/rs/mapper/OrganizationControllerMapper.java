package com.alkemy.ong.ports.input.rs.mapper;

import com.alkemy.ong.domain.model.Organization;
import com.alkemy.ong.ports.input.rs.request.UpdateOrganizationRequest;
import com.alkemy.ong.ports.input.rs.response.OrganizationResponse;
import org.mapstruct.Mapper;

@Mapper
public interface OrganizationControllerMapper extends CommonMapper{

    OrganizationResponse organizationToOrganizationResponse(Organization organization);

    Organization updateOrganizationRequestToOrganization(UpdateOrganizationRequest updateOrganizationRequest);


}

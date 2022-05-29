package com.alkemy.ong.ports.input.rs.mapper;

import com.alkemy.ong.domain.model.Organization;
import com.alkemy.ong.ports.input.rs.response.OrganizationResponse;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper
public interface OrganizationControllerMapper extends CommonMapper{

    List<OrganizationResponse> organizationListToOrganizationResponseList(List<Organization> organizations);

}

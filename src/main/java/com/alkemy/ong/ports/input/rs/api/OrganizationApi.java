package com.alkemy.ong.ports.input.rs.api;

import com.alkemy.ong.ports.input.rs.response.OrganizationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;


@Validated
public interface OrganizationApi {

    ResponseEntity<OrganizationResponse> getOrganization(@NotNull Long id);

}

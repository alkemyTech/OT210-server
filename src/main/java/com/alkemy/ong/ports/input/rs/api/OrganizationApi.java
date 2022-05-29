package com.alkemy.ong.ports.input.rs.api;

import com.alkemy.ong.ports.input.rs.response.OrganizationResponseList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Validated
public interface OrganizationApi {

    @Operation(summary = "Get Organization List", description = "Get Organization List", responses = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = OrganizationResponseList.class))})})
    ResponseEntity<OrganizationResponseList> getOrganizations(Optional<Integer> page, Optional<Integer> size);
}

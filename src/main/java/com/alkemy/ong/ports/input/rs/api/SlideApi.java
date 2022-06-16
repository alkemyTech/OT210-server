package com.alkemy.ong.ports.input.rs.api;

import com.alkemy.ong.common.exception.error.ErrorDetails;
import com.alkemy.ong.ports.input.rs.response.ContactResponseList;
import com.alkemy.ong.ports.input.rs.response.SlideResponseList;
import com.alkemy.ong.ports.input.rs.response.SlideResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Optional;

import javax.validation.constraints.NotNull;

@SecurityRequirement(name = "bearerAuth")
@Validated
public interface SlideApi {

    ResponseEntity<SlideResponse> getById(@NotNull Long id);
    ResponseEntity<Void> deleteSlide(@NotNull Long id);

    @Operation(summary = "Get Slide List", description = "Get Slide List", responses = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ContactResponseList.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorDetails.class),
                            examples = @ExampleObject(value = "{\"code\":\"BAD_CREDENTIALS\",\"detail\":\"The server cannot return a response due to invalid credentials.\"}"))}),
    })
    ResponseEntity<SlideResponseList> getSlides(Optional<Integer> page, Optional<Integer> size);
}

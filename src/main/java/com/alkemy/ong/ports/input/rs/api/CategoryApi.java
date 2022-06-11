package com.alkemy.ong.ports.input.rs.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import com.alkemy.ong.ports.input.rs.request.CreateCategoryRequest;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@SecurityRequirement(name = "bearerAuth")
@Validated
public interface CategoryApi {

    ResponseEntity<Void> createCategory(@Valid CreateCategoryRequest createCategoryRequest);
    ResponseEntity<Void> deleteCategory(@NotNull Long id);

}

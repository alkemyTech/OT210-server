package com.alkemy.ong.ports.input.rs.api;

import com.alkemy.ong.ports.input.rs.request.CreateCategoryRequest;
import com.alkemy.ong.ports.input.rs.response.CategoryResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Validated
public interface CategoryApi {

    ResponseEntity<Void> createCategory(@Valid CreateCategoryRequest createCategoryRequest);

    ResponseEntity<Void> deleteCategory(@NotNull Long id);

    ResponseEntity<CategoryResponse> getCategory(@NotNull Long id);

    void updateCategory(@NotNull Long id, @Valid CreateCategoryRequest createCategoryRequest);

}

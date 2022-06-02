package com.alkemy.ong.ports.input.rs.api;

import com.alkemy.ong.ports.input.rs.request.CreateCategoryRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
public interface CategoryApi {

    ResponseEntity<Void> createCategory(@Valid CreateCategoryRequest createCategoryRequest);

}

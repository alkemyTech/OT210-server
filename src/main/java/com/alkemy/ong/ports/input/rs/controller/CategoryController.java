package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.domain.usecase.CategoryService;
import com.alkemy.ong.ports.input.rs.api.CategoryApi;
import com.alkemy.ong.ports.input.rs.mapper.CategoryControllerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

import static com.alkemy.ong.ports.input.rs.api.ApiConstants.CATEGORIES_URI;

@RestController
@RequestMapping(CATEGORIES_URI)
@RequiredArgsConstructor
public class CategoryController implements CategoryApi {

    private final CategoryControllerMapper mapper;

    private final CategoryService service;

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@NotNull @PathVariable Long id) {
        service.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

package com.alkemy.ong.ports.input.rs.api;

import com.alkemy.ong.ports.input.rs.response.CategoryResponseList;
import org.springframework.http.ResponseEntity;
import com.alkemy.ong.ports.input.rs.request.CreateCategoryRequest;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Validated
public interface CategoryApi {

    ResponseEntity<Void> createCategory(@Valid CreateCategoryRequest createCategoryRequest);
    ResponseEntity<Void> deleteCategory(@NotNull Long id);
    ResponseEntity<CategoryResponseList> getCategories(Optional<Integer> page, Optional<Integer> size);

}

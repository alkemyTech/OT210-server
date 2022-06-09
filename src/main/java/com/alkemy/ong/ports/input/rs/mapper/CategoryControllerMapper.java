package com.alkemy.ong.ports.input.rs.mapper;

import com.alkemy.ong.domain.model.Category;
import com.alkemy.ong.ports.input.rs.request.CreateCategoryRequest;
import com.alkemy.ong.ports.input.rs.response.CategoryResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface CategoryControllerMapper extends CommonMapper {

    Category createCategoryRequestToCategory(CreateCategoryRequest create);
    List<CategoryResponse> categoryListToCategoryResponseList(List<Category> categories);
    CategoryResponse categoryToCategoryResponse(Category category);

}

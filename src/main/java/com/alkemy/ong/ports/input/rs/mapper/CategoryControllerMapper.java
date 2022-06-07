package com.alkemy.ong.ports.input.rs.mapper;

import com.alkemy.ong.domain.model.Category;
import com.alkemy.ong.ports.input.rs.request.CreateCategoryRequest;
import com.alkemy.ong.ports.input.rs.request.UpdateCategoryRequest;
import org.mapstruct.Mapper;

@Mapper
public interface CategoryControllerMapper extends CommonMapper {

    Category createCategoryRequestToCategory(CreateCategoryRequest create);
    Category updateCategoryRequestToCategory(UpdateCategoryRequest create);

}

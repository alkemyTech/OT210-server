package com.alkemy.ong.domain.usecase;

import com.alkemy.ong.domain.model.Category;

public interface CategoryService {

    Long createEntity(Category category);
    void deleteCategory(Long id);
    Category getByIdIfExists(Long id);

}

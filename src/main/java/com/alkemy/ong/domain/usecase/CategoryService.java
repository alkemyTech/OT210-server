package com.alkemy.ong.domain.usecase;

import com.alkemy.ong.domain.model.Category;
import com.alkemy.ong.domain.model.CategoryList;
import org.springframework.data.domain.PageRequest;

public interface CategoryService {

    Long createEntity(Category category);
    void deleteCategory(Long id);
    CategoryList getList(PageRequest pageRequest);

}

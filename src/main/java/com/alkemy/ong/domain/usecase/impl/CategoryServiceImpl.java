package com.alkemy.ong.domain.usecase.impl;

import com.alkemy.ong.common.exception.NotFoundException;
import com.alkemy.ong.domain.model.Category;
import com.alkemy.ong.domain.model.CategoryList;
import com.alkemy.ong.domain.repository.CategoryRepository;
import com.alkemy.ong.domain.usecase.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryJpaRepository;

    @Override
    @Transactional
    public Long createEntity(Category category) {
        return categoryJpaRepository.save(category).getId();
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        categoryJpaRepository.findById(id).ifPresent(categoryJpaRepository::delete);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryList getList(PageRequest pageRequest) {
        Page<Category> page = categoryJpaRepository.findAll(pageRequest);
        return new CategoryList(page.getContent(), pageRequest, page.getTotalElements());
    }

    @Transactional(readOnly = true)
    public Category getByIdIfExists(Long id) {
        return categoryJpaRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    @Transactional
    public void updateCategoryIfExists(Long id, Category category) {
        categoryJpaRepository.findById(id)
                .map(categoryJpa -> {
                    categoryJpa.setName(category.getName());
                    categoryJpa.setDescription(category.getDescription());
                    categoryJpa.setImage(category.getImage());

                    return categoryJpaRepository.save(categoryJpa);
                }).orElseThrow(() -> new NotFoundException(id));
    }

}

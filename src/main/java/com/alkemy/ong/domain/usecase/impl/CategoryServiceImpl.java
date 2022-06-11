package com.alkemy.ong.domain.usecase.impl;

import com.alkemy.ong.common.exception.NotFoundException;
import com.alkemy.ong.domain.model.Alkymer;
import com.alkemy.ong.domain.model.Category;
import com.alkemy.ong.domain.repository.CategoryRepository;
import com.alkemy.ong.domain.usecase.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

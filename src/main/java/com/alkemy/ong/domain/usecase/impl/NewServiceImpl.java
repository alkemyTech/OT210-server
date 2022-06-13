package com.alkemy.ong.domain.usecase.impl;

import com.alkemy.ong.common.exception.NotFoundException;
import com.alkemy.ong.domain.model.Category;
import com.alkemy.ong.domain.model.New;
import com.alkemy.ong.domain.repository.CategoryRepository;
import com.alkemy.ong.domain.repository.NewRepository;
import com.alkemy.ong.domain.usecase.NewService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class NewServiceImpl implements NewService {

    private final NewRepository newJpaRepository;
    private final CategoryRepository categoryJpaRepository;

    @Value("${default.category.id}")
    private Long defaultCategoryId;

    @Override
    @Transactional
    public Long createNew(New entity) {
        entity.setCategory(getCategoryIfExists(defaultCategoryId));
        return newJpaRepository.save(entity).getId();
    }
    private Category getCategoryIfExists(Long categoryId){
        return  categoryJpaRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException(categoryId));
    }
}

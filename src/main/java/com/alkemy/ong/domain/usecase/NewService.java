package com.alkemy.ong.domain.usecase;

import com.alkemy.ong.domain.model.New;
import com.alkemy.ong.domain.model.NewList;
import org.springframework.data.domain.PageRequest;

public interface NewService {

    Long createNew(New entity);

    NewList getList(PageRequest pageRequest);

    void deleteById(Long id);

    New updateNew(Long id,New entity);
}

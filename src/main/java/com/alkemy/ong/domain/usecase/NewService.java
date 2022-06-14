package com.alkemy.ong.domain.usecase;

import com.alkemy.ong.domain.model.New;
import com.alkemy.ong.domain.model.NewList;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

public interface NewService {

    Long createNew(New entity);

    NewList getList(PageRequest pageRequest);
}

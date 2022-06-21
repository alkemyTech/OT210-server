package com.alkemy.ong.domain.usecase;

import com.alkemy.ong.domain.model.CommentList;
import com.alkemy.ong.domain.model.New;
import com.alkemy.ong.domain.model.NewList;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

public interface NewService {

    Long createNew(New entity);

    NewList getList(PageRequest pageRequest);

    void deleteById(Long id);

    New updateNew(Long id,New entity);

    New getNewById(Long id);

    @Transactional
    CommentList getCommentsFromNew(Long id, PageRequest pageRequest);
}

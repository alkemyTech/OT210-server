package com.alkemy.ong.domain.usecase;

import com.alkemy.ong.domain.model.Comment;

public interface CommentService {

    Long createEntity(Comment entity);
    void deleteById(Long id);
    void updateEntityIfExists(Long id, Comment entity);

}

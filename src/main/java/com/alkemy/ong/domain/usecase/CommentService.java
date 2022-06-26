package com.alkemy.ong.domain.usecase;

import com.alkemy.ong.domain.model.Comment;
import com.alkemy.ong.domain.model.CommentList;
import org.springframework.data.domain.PageRequest;

public interface CommentService {

    Long createEntity(Comment entity, Long newId);
    void deleteById(Long id);
    Comment updateEntityIfExists(Long id, Comment entity);
    CommentList getComments(PageRequest pageRequest);

}

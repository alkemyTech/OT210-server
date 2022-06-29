package com.alkemy.ong.domain.usecase;

import com.alkemy.ong.domain.model.Comment;
import com.alkemy.ong.domain.model.CommentList;
import com.alkemy.ong.domain.model.User;
import org.springframework.data.domain.PageRequest;

public interface CommentService {

    Long createEntity(Comment entity, Long newId);

    void deleteById(Long id, User user);

    void updateEntityIfExists(Long id, Comment entity);

    CommentList getComments(PageRequest pageRequest);
}

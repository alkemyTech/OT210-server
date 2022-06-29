package com.alkemy.ong.domain.usecase;

import com.alkemy.ong.domain.model.Comment;
import com.alkemy.ong.domain.model.CommentList;
import com.alkemy.ong.domain.model.User;
import org.springframework.data.domain.PageRequest;

public interface CommentService {
    Long createEntity(Comment entity, Long newId);
    void updateEntityIfExists(Long id, Long newId, Comment entity, User user);
    void deleteById(Long id, User user);
    CommentList getComments(PageRequest pageRequest);
}

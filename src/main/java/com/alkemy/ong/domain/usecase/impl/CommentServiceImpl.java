package com.alkemy.ong.domain.usecase.impl;

import com.alkemy.ong.common.exception.NotFoundException;
import com.alkemy.ong.domain.model.Comment;
import com.alkemy.ong.domain.repository.CommentRepository;
import com.alkemy.ong.domain.usecase.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentJpaRepository;

    @Override
    @Transactional
    public Long createEntity(Comment comment) {
        return commentJpaRepository.save(comment).getId();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        commentJpaRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void updateEntityIfExists(Long id, Comment comment) {
        commentJpaRepository.findById(id)
                .map(commentJpa -> {
                    Optional.ofNullable(comment.getBody()).ifPresent(commentJpa::setBody);
                    return commentJpaRepository.save(commentJpa);
                }).orElseThrow(() -> new NotFoundException(id));
    }

}

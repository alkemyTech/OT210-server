package com.alkemy.ong.domain.usecase.impl;

import com.alkemy.ong.common.exception.NotFoundException;
import com.alkemy.ong.domain.model.Comment;
import com.alkemy.ong.domain.model.CommentList;
import com.alkemy.ong.domain.repository.CommentRepository;
import com.alkemy.ong.domain.usecase.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    @Override
    @Transactional(readOnly = true)
    public CommentList getComments(PageRequest pageRequest) {
        Page<Comment> page = commentJpaRepository.findAll(PageRequest.of(pageRequest.getPageNumber(), pageRequest.getPageSize(),
                Sort.by(Sort.Direction.DESC, "audit.createdAt")));
        return new CommentList(page.getContent(), pageRequest, page.getTotalElements());
    }

}

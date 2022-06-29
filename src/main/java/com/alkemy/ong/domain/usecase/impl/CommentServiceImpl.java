package com.alkemy.ong.domain.usecase.impl;

import com.alkemy.ong.common.exception.NotFoundException;
import com.alkemy.ong.domain.model.Comment;
import com.alkemy.ong.domain.model.CommentList;
import com.alkemy.ong.domain.model.New;
import com.alkemy.ong.domain.model.User;
import com.alkemy.ong.domain.repository.CommentRepository;
import com.alkemy.ong.domain.repository.NewRepository;
import com.alkemy.ong.domain.usecase.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final NewRepository newRepository;

    @Override
    @Transactional
    public Long createEntity(Comment comment, Long newId) {
        New new_ = newRepository.findById(newId).orElseThrow(() -> new NotFoundException(newId));
        comment.setNew_(new_);
        return commentRepository.save(comment).getId();
    }

    @Override
    @Transactional
    public void deleteById(Long id, User user) {

        commentRepository.findById(id).ifPresent(comment -> {
            if (isAuthorized(comment, user)) {
                commentRepository.delete(comment);
            } else {
                throw new AccessDeniedException("User not authorized to delete this resource");
            }
        });
    }

    @Override
    @Transactional
    public void updateEntityIfExists(Long id, Long newId, Comment comment, User user) {
        Comment toUpdate = commentRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        if (isAuthorized(toUpdate, user)) {
            New new_ = newRepository.findById(newId).orElseThrow(() -> new NotFoundException(newId));
            toUpdate.setNew_(new_);
            toUpdate.setBody(comment.getBody());
            commentRepository.save(toUpdate);
        } else {
            throw new AccessDeniedException("User not authorized to update this resource");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public CommentList getComments(PageRequest pageRequest) {
        Page<Comment> page = commentRepository.findAll(PageRequest.of(pageRequest.getPageNumber(), pageRequest.getPageSize(),
                Sort.by(Sort.Direction.DESC, "audit.createdAt")));
        return new CommentList(page.getContent(), pageRequest, page.getTotalElements());
    }

    private boolean isAuthorized(Comment comment, User user) {
        return Objects.equals(user, comment.getUser()) ||
                Objects.equals(user.getRole().getAuthority(), "ROLE_ADMIN");
    }
}

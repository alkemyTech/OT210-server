package com.alkemy.ong.ports.input.rs.api;

import com.alkemy.ong.ports.input.rs.request.CreateCommentRequest;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;

public interface CommentApi {
    ResponseEntity<Void> createComment(@Valid CreateCommentRequest createCommentRequest);
}

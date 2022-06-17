package com.alkemy.ong.ports.input.rs.api;

import com.alkemy.ong.ports.input.rs.response.CommentResponseList;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Validated
public interface CommentApi {

    ResponseEntity<CommentResponseList> getComments(Optional<Integer> page, Optional<Integer> size);
}

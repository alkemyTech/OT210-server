package com.alkemy.ong.ports.input.rs.api;

import com.alkemy.ong.domain.model.User;
import com.alkemy.ong.ports.input.rs.request.CreateCommentRequest;
import com.alkemy.ong.ports.input.rs.response.CommentResponseList;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Optional;


@Validated
public interface CommentApi {

    ResponseEntity<CommentResponseList> getComments(Optional<Integer> page, Optional<Integer> size);

    ResponseEntity<Void> createComment(@Valid CreateCommentRequest createCommentRequest,
                                       @AuthenticationPrincipal User user);

}

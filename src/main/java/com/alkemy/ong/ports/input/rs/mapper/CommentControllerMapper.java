package com.alkemy.ong.ports.input.rs.mapper;

import com.alkemy.ong.domain.model.Comment;
import com.alkemy.ong.domain.model.User;
import com.alkemy.ong.ports.input.rs.request.CreateCommentRequest;
import com.alkemy.ong.ports.input.rs.request.UpdateCommentRequest;
import com.alkemy.ong.ports.input.rs.response.CommentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface CommentControllerMapper extends CommonMapper {

    List<CommentResponse> commentListToCommentResponseList(List<Comment> comments);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "audit", ignore = true)
    @Mapping(target = "user", source = "user")
    Comment createCommentRequestToComment(CreateCommentRequest request, User user);

    Comment updateCommentRequestToComment(UpdateCommentRequest commentRequest);
}

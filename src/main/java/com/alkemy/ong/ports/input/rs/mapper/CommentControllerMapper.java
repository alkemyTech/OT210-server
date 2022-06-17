package com.alkemy.ong.ports.input.rs.mapper;

import com.alkemy.ong.domain.model.Comment;
import com.alkemy.ong.ports.input.rs.response.CommentResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface CommentControllerMapper extends CommonMapper{

    List<CommentResponse> commentListToCommentResponseList(List<Comment> comments);

}

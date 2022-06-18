package com.alkemy.ong.domain.model;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class CommentList extends PageImpl<Comment> {
    public CommentList(List<Comment> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
}

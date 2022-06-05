package com.alkemy.ong.domain.model;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class UserList extends PageImpl<User> {
    public UserList(List<User> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
}

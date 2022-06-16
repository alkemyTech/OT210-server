package com.alkemy.ong.domain.model;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class NewList extends PageImpl<New> {
    public NewList(List<New> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

}

package com.alkemy.ong.domain.usecase;

import com.alkemy.ong.domain.model.Member;
import com.alkemy.ong.domain.model.MemberList;
import org.springframework.data.domain.PageRequest;

public interface MemberService {

    Long createEntity(Member entity);

    MemberList getList(PageRequest pageRequest);

    void deleteMember(Long id);
}

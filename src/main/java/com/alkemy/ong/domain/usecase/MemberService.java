package com.alkemy.ong.domain.usecase;

import com.alkemy.ong.domain.model.Member;

public interface MemberService {

    Long createEntity(Member entity);

    void updateMember(Long id, Member entity);
}
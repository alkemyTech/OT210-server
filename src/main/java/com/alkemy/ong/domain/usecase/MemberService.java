package com.alkemy.ong.domain.usecase;

import com.alkemy.ong.domain.model.Member;
import com.alkemy.ong.ports.input.rs.response.MemberResponse;

public interface MemberService {

    Long createEntity(Member entity);

    MemberResponse updateMember(Long id, Member entity);
}
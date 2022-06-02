package com.alkemy.ong.domain.usecase.impl;

import com.alkemy.ong.domain.model.Member;
import com.alkemy.ong.domain.repository.MemberRepository;
import com.alkemy.ong.domain.usecase.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberJpaRepository;

    @Override
    @Transactional
    public Long createEntity(Member member) {
        return memberJpaRepository.save(member).getId();
    }
}

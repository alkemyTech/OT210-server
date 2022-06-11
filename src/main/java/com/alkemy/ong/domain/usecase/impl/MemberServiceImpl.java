package com.alkemy.ong.domain.usecase.impl;

import com.alkemy.ong.domain.model.Member;
import com.alkemy.ong.domain.repository.MemberRepository;
import com.alkemy.ong.domain.usecase.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberJpaRepository;

    @Override
    @Transactional
    public Long createEntity(Member member) {
        return memberJpaRepository.save(member).getId();
    }

    @Override
    @Transactional
    public void deleteMember(Long id) {
        Optional<Member> optional = memberJpaRepository.findById(id);
        if (optional.isPresent()) {
            Member member = optional.get();
            memberJpaRepository.delete(member);
        }
    }
}

package com.alkemy.ong.domain.usecase.impl;

import com.alkemy.ong.common.exception.NotFoundException;
import com.alkemy.ong.domain.model.Member;
import com.alkemy.ong.domain.model.MemberList;
import com.alkemy.ong.domain.repository.MemberRepository;
import com.alkemy.ong.domain.usecase.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public void updateMember(Long id, Member entity) {

        Optional<Member> optional = memberJpaRepository.findById(id);
        if (optional.isPresent()) {
            Member memberToUpdate = optional.get();
            memberToUpdate.setName(entity.getName());
            memberToUpdate.setImage(entity.getImage());
            memberToUpdate.setDescription(entity.getDescription());
            memberToUpdate.setFacebookUrl(entity.getFacebookUrl());
            memberToUpdate.setInstagramUrl(entity.getInstagramUrl());
            memberToUpdate.setLinkedinUrl(entity.getLinkedinUrl());
            memberJpaRepository.save(memberToUpdate);
        } else {
            throw new NotFoundException(id);
        }
    }

    @Transactional(readOnly = true)
    public MemberList getList(PageRequest pageRequest) {
        Page<Member> page = memberJpaRepository.findAll(pageRequest);
        return new MemberList(page.getContent(), pageRequest, page.getTotalElements());
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

package com.alkemy.ong.domain.usecase.impl;

import com.alkemy.ong.common.exception.NotFoundException;
import com.alkemy.ong.domain.model.Member;
import com.alkemy.ong.domain.repository.MemberRepository;
import com.alkemy.ong.domain.usecase.MemberService;
import com.alkemy.ong.ports.input.rs.mapper.MemberControllerMapperImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
            memberToUpdate.getAudit().setUpdatedAt(LocalDateTime.now());
            memberJpaRepository.save(memberToUpdate);
        } else {
            throw new NotFoundException(id);
        }
    }
}
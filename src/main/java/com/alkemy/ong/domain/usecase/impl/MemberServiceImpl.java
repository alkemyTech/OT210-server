package com.alkemy.ong.domain.usecase.impl;

import com.alkemy.ong.common.exception.NotFoundException;
import com.alkemy.ong.domain.model.Member;
import com.alkemy.ong.domain.repository.MemberRepository;
import com.alkemy.ong.domain.usecase.MemberService;
import com.alkemy.ong.ports.input.rs.mapper.MemberControllerMapperImpl;
import com.alkemy.ong.ports.input.rs.response.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberJpaRepository;

    private final MemberControllerMapperImpl mapper;

    @Override
    @Transactional
    public Long createEntity(Member member) {
        return memberJpaRepository.save(member).getId();
    }

    @Override
    @Transactional
    public MemberResponse updateMember(Long id, Member entity) {

        Optional<Member> optional = memberJpaRepository.findById(id);
        if (optional.isPresent()) {
            try {
                Member memberToUpdate = memberJpaRepository.getById(id);
                memberToUpdate.setName(entity.getName());
                memberToUpdate.setImage(entity.getImage());
                memberToUpdate.setDescription(entity.getDescription());
                memberToUpdate.setFacebookUrl(entity.getFacebookUrl());
                memberToUpdate.setInstagramUrl(entity.getInstagramUrl());
                memberToUpdate.setLinkedinUrl(entity.getLinkedinUrl());
                memberToUpdate.getAudit().setUpdatedAt(LocalDateTime.now());
                memberJpaRepository.save(memberToUpdate);
                return mapper.memberToMemberResponse(memberToUpdate);
            } catch (Exception exception) {
                throw new NotFoundException(id);
            }
        } else {
            throw new NotFoundException(id);
        }
    }
}
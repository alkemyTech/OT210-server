package com.alkemy.ong.ports.input.rs.mapper;

import com.alkemy.ong.domain.model.Member;
import com.alkemy.ong.ports.input.rs.request.CreateMemberRequest;
import com.alkemy.ong.ports.input.rs.response.MemberResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface MemberControllerMapper extends CommonMapper {

    Member createMemberRequestToMember(CreateMemberRequest create);

    List<MemberResponse> memberListToMemberResponseList(List<Member> members);

    MemberResponse memberToMemberResponse(Member member);
}
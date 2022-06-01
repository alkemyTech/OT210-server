package com.alkemy.ong.ports.input.rs.mapper;

import com.alkemy.ong.domain.model.Member;
import com.alkemy.ong.ports.input.rs.request.CreateMemberRequest;
import org.mapstruct.Mapper;

@Mapper
public interface MemberControllerMapper extends CommonMapper{

    Member createMemberRequestToMember(CreateMemberRequest create);
}

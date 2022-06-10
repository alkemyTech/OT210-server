package com.alkemy.ong.ports.input.rs.api;

import com.alkemy.ong.ports.input.rs.request.CreateMemberRequest;
import com.alkemy.ong.ports.input.rs.response.MemberResponseList;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Optional;

@Validated
public interface MemberApi {
    ResponseEntity<Void> createMember(@Valid CreateMemberRequest createMemberRequest);

    ResponseEntity<MemberResponseList> getMembers(Optional<Integer> page, Optional<Integer> size);
}
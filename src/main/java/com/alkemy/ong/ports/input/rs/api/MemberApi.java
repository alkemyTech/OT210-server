package com.alkemy.ong.ports.input.rs.api;

import com.alkemy.ong.ports.input.rs.request.CreateMemberRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Validated
public interface MemberApi {
    ResponseEntity<Void> createMember(@Valid CreateMemberRequest createMemberRequest);

    ResponseEntity<Void> deleteMember(@NotNull Long id);

}
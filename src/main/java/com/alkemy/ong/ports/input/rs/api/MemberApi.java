package com.alkemy.ong.ports.input.rs.api;

import com.alkemy.ong.ports.input.rs.request.CreateMemberRequest;
import com.alkemy.ong.ports.input.rs.request.UpdateMemberRequest;
import com.alkemy.ong.ports.input.rs.response.MemberResponseList;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;


@SecurityRequirement(name = "bearerAuth")
@Validated
public interface MemberApi {
    ResponseEntity<Void> createMember(@Valid CreateMemberRequest createMemberRequest);

    void updateMember(@NotNull Long id, @Valid UpdateMemberRequest updateMemberRequest);

    ResponseEntity<MemberResponseList> getMembers(Optional<Integer> page, Optional<Integer> size);

    ResponseEntity<Void> deleteMember(@NotNull Long id);

}

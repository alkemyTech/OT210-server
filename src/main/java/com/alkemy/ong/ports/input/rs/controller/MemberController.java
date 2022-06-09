package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.domain.model.Member;
import com.alkemy.ong.domain.usecase.MemberService;
import com.alkemy.ong.ports.input.rs.api.MemberApi;
import com.alkemy.ong.ports.input.rs.mapper.MemberControllerMapper;
import com.alkemy.ong.ports.input.rs.request.CreateMemberRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;

import static com.alkemy.ong.ports.input.rs.api.ApiConstants.MEMBERS_URI;

@RestController
@RequestMapping(MEMBERS_URI)
@RequiredArgsConstructor
public class MemberController implements MemberApi {

    private final MemberService service;

    private final MemberControllerMapper mapper;

    @Override
    @PostMapping
    public ResponseEntity<Void> createMember(@Valid @RequestBody CreateMemberRequest createMemberRequest) {

        Member member = mapper.createMemberRequestToMember(createMemberRequest);

        final long id = service.createEntity(member);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(id)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@NotNull @PathVariable Long id) {
        service.deleteMember(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

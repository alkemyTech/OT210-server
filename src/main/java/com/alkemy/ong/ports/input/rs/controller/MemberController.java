package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.domain.model.Member;
import com.alkemy.ong.domain.model.MemberList;
import com.alkemy.ong.domain.usecase.MemberService;
import com.alkemy.ong.ports.input.rs.api.ApiConstants;
import com.alkemy.ong.ports.input.rs.api.MemberApi;
import com.alkemy.ong.ports.input.rs.mapper.MemberControllerMapper;
import com.alkemy.ong.ports.input.rs.request.CreateMemberRequest;
import com.alkemy.ong.ports.input.rs.response.MemberResponse;
import com.alkemy.ong.ports.input.rs.response.MemberResponseList;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

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
    @GetMapping
    public ResponseEntity<MemberResponseList> getMembers(Optional<Integer> page, Optional<Integer> size) {

        final int pageNumber = page.filter(p -> p > 0).orElse(ApiConstants.DEFAULT_PAGE);
        final int pageSize = size.filter(s -> s > 0).orElse(ApiConstants.DEFAULT_PAGE_SIZE);

        MemberList memberList = service.getList(PageRequest.of(pageNumber, pageSize));

        MemberResponseList responseList;
        {
            responseList = new MemberResponseList();

            List<MemberResponse> memberResponseList = mapper.memberListToMemberResponseList(memberList.getContent());
            responseList.setContent(memberResponseList);

            final int nextPage = memberList.getPageable().next().getPageNumber();
            responseList.setNextUri(ApiConstants.uriByPageAsString.apply(nextPage));

            final int previousPage = memberList.getPageable().previousOrFirst().getPageNumber();
            responseList.setPreviousUri(ApiConstants.uriByPageAsString.apply(previousPage));

            responseList.setTotalPages(memberList.getTotalPages());
            responseList.setTotalElements(memberList.getTotalElements());
        }
        return ResponseEntity.ok().body(responseList);
    }
}
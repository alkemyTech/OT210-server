package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.domain.model.User;
import com.alkemy.ong.domain.model.UserList;
import com.alkemy.ong.domain.usecase.UserService;
import com.alkemy.ong.ports.input.rs.api.ApiConstants;
import com.alkemy.ong.ports.input.rs.api.UserApi;
import com.alkemy.ong.ports.input.rs.mapper.UserControllerMapper;
import com.alkemy.ong.ports.input.rs.request.UpdateUserRequest;
import com.alkemy.ong.ports.input.rs.response.UserResponse;
import com.alkemy.ong.ports.input.rs.response.UserResponseList;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

import static com.alkemy.ong.ports.input.rs.api.ApiConstants.USERS_URI;

@RestController
@RequestMapping(USERS_URI)
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserService service;

    private final UserControllerMapper mapper;

    @Override
    @GetMapping
    public ResponseEntity<UserResponseList> getUsers(@RequestParam Optional<Integer> page,
                                                     @RequestParam Optional<Integer> size) {

        final int pageNumber = page.filter(p -> p > 0).orElse(ApiConstants.DEFAULT_PAGE);
        final int pageSize = size.filter(s -> s > 0).orElse(ApiConstants.DEFAULT_PAGE_SIZE);

        UserList list = service.getList(PageRequest.of(pageNumber, pageSize));

        UserResponseList response;
        {
            response = new UserResponseList();

            List<UserResponse> content = mapper.userListToUserListResponse(list.getContent());
            response.setContent(content);

            final int nextPage = list.getPageable().next().getPageNumber();
            response.setNextUri(ApiConstants.uriByPageAsString.apply(nextPage));

            final int previousPage = list.getPageable().previousOrFirst().getPageNumber();
            response.setPreviousUri(ApiConstants.uriByPageAsString.apply(previousPage));

            response.setTotalPages(list.getTotalPages());
            response.setTotalElements(list.getTotalElements());

        }

        return ResponseEntity.ok().body(response);
    }


    @Override
    @PatchMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@NotNull @PathVariable("id") Long id,
                                                   @RequestBody UpdateUserRequest updateUserRequest) {

        User toEntity = mapper.updateUserRequestToUser(updateUserRequest);
        UserResponse userResponse = service.updateUser(id, toEntity);
        return ResponseEntity.ok().body(userResponse);

    }
}

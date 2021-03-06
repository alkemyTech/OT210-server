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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
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
                                                   @Valid @RequestBody UpdateUserRequest userRequest,
                                                   @AuthenticationPrincipal User user) {

        boolean canUpdate = Objects.equals(user.getId(), id) ||
                Objects.equals(user.getRole().getAuthority(), "ROLE_ADMIN");
        if (canUpdate) {
            User toEntity = mapper.updateUserRequestToUser(userRequest);
            User userToUpdate = service.updateUser(id, toEntity);
            UserResponse userResponse = mapper.userToUserResponse(userToUpdate);
            return ResponseEntity.ok().body(userResponse);
        }
        throw new AccessDeniedException("User not authorized to update this resource");
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@NotNull @PathVariable Long id) {
        service.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

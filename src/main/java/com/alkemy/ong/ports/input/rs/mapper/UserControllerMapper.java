package com.alkemy.ong.ports.input.rs.mapper;

import com.alkemy.ong.domain.model.User;
import com.alkemy.ong.ports.input.rs.response.UserResponse;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper
public interface UserControllerMapper extends CommonMapper{

    @IterableMapping(qualifiedByName = "userToUserResponse")
    List<UserResponse> userListToUserListResponse(List<User> users);

    @Named("userToUserResponse")
    UserResponse userToUserResponse(User user);
}

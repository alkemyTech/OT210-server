package com.alkemy.ong.ports.input.rs.mapper;

import com.alkemy.ong.domain.model.User;
import com.alkemy.ong.ports.input.rs.request.CreateUserRequest;
import com.alkemy.ong.ports.input.rs.response.UserResponse;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper
public interface UserControllerMapper extends CommonMapper{

    @IterableMapping(qualifiedByName = "userToUserResponse")
    List<UserResponse> userListToUserListResponse(List<User> users);


    @Named("userToUserResponse")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source ="lastName")
    @Mapping(target = "email", source ="email")
    @Mapping(target = "photo" , source = "photo")
    UserResponse userToUserResponse(User user);

    @Named("createUserRequestToUser")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source ="lastName")
    @Mapping(target = "email", source ="email")
    @Mapping(target = "photo", source ="photo")
    @Mapping(target = "password", source ="password")
    User createUserRequestToUser(CreateUserRequest createUserRequest);
}

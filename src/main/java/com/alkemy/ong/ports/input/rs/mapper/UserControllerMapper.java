package com.alkemy.ong.ports.input.rs.mapper;

import com.alkemy.ong.domain.model.User;
import com.alkemy.ong.ports.input.rs.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper
public interface UserControllerMapper extends CommonMapper{

    @Named("userToUserResponse")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "firstName", source = "firstname")
    @Mapping(target = "lastName", source ="lastName")
    @Mapping(target = "email", source ="email")
    @Mapping(target = "photo" , source = "photo")
    UserResponse userToUserResponse(User user);
}

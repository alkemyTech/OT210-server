package com.alkemy.ong.domain.usecase;

import com.alkemy.ong.domain.model.User;
import com.alkemy.ong.domain.model.UserList;
import com.alkemy.ong.ports.input.rs.request.CreateUserRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserList getList(PageRequest pageRequest);
    User registerNewUser(CreateUserRequest createUserRequest);
}

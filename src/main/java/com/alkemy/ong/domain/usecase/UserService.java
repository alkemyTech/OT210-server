package com.alkemy.ong.domain.usecase;

import com.alkemy.ong.domain.model.User;
import com.alkemy.ong.domain.model.UserList;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserList getList(PageRequest pageRequest);

    User updateUser(Long id, User entity);

    User registerNewUser(User user);

    void deleteUser(Long id);
}

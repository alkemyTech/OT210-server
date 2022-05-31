package com.alkemy.ong.domain.usecase;


import com.alkemy.ong.domain.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User getByEmail(String email);


}

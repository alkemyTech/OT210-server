package com.alkemy.ong.domain.usecase.impl;

import com.alkemy.ong.domain.repository.UserRepository;
import com.alkemy.ong.domain.usecase.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        com.alkemy.ong.domain.model.User userEntity = userRepository.findByEmail(email);

        if (userEntity != null) {

            List<GrantedAuthority> permission = new ArrayList<>();
            GrantedAuthority permission1 = new SimpleGrantedAuthority("ROLE_" + userEntity.getRole());
            permission.add(permission1);
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attributes.getRequest().getSession(true);
            session.setAttribute("user-session", userEntity);
            return new org.springframework.security.core.userdetails.User(userEntity.getEmail(), userEntity.getPassword(), permission);

        } else {
            return null;
        }
    }
}
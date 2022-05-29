package com.alkemy.ong.domain.usecase.impl;

import com.alkemy.ong.common.exception.Message.ExceptionMessage;
import com.alkemy.ong.common.exception.RegisterException;
import com.alkemy.ong.domain.model.User;
import com.alkemy.ong.domain.repository.UserRepository;
import com.alkemy.ong.domain.usecase.UserService;
import com.alkemy.ong.ports.input.rs.request.CreateUserRequest;
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
import java.io.IOException;
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
    public void create(CreateUserRequest createUserRequest) throws IOException {

        if (ifUsernameExist(createUserRequest.getEmail())) {
            throw new RegisterException(ExceptionMessage.USER_EXISTS);
        }

        User userEntity = new User();

        userEntity.setFirstName(createUserRequest.getFirstName());
        userEntity.setLastName(createUserRequest.getLastName());
        userEntity.setUsername(createUserRequest.getEmail());
        String encryptedPassword = passwordEncoder.encode(createUserRequest.getPassword());
        userEntity.setPassword(encryptedPassword);
        userEntity.setPhoto(createUserRequest.getPhoto());
        userEntity.setRole(createUserRequest.getRole());

        userRepository.save(userEntity);
    }

    public Boolean ifUsernameExist(String email) {
        return userRepository.findByEmail(email) != null;
    }

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
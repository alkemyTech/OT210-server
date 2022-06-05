package com.alkemy.ong.domain.usecase.impl;

import com.alkemy.ong.domain.model.User;
import com.alkemy.ong.domain.model.UserList;
import com.alkemy.ong.domain.repository.UserRepository;
import com.alkemy.ong.domain.usecase.UserService;
import com.alkemy.ong.ports.input.rs.request.CreateUserRequest;
import com.alkemy.ong.ports.output.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userJpaRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) {
        return userJpaRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("username %s not found".formatted(email)));
    }

    @Override
    @Transactional(readOnly = true)
    public UserList getList(PageRequest pageRequest) {
        Page<User> page = userJpaRepository.findAll(pageRequest);
        return new UserList(page.getContent(),pageRequest,page.getTotalElements());
    }

    @Override
    @Transactional
    public User registerNewUser(CreateUserRequest userRequest) {

        if (emailExists(userRequest.getEmail())) {
            throw new RuntimeException(
                    "There is already an account with that email address: " + userRequest.getEmail()
            );
        }
        User user = makeUserFromRequest(userRequest);

//        emailService.sendWelcomeEmail(user.getEmail(), someOrganization);

        return userJpaRepository.save(user);
    }

    private User makeUserFromRequest(CreateUserRequest userRequest) {
        User user = new User();

        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());

        user.setPassword(passwordEncoder.encode(
                userRequest.getPassword()
        ));

        return user;
    }

    private boolean emailExists(String email) {
        return userJpaRepository.findByEmail(email)
                .isPresent();
    }
}

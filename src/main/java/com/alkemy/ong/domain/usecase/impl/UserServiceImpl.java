package com.alkemy.ong.domain.usecase.impl;

import com.alkemy.ong.common.exception.ConflictException;
import com.alkemy.ong.domain.model.User;
import com.alkemy.ong.domain.model.UserList;
import com.alkemy.ong.domain.repository.UserRepository;
import com.alkemy.ong.domain.usecase.OrganizationService;
import com.alkemy.ong.domain.usecase.UserService;
import com.alkemy.ong.ports.output.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
    private final EmailService emailService;
    private final OrganizationService organizationService;
    private final PasswordEncoder passwordEncoder;
    @Value("${main.organization.id}")
    private Long id;

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
    public User registerNewUser(User user) {

        if (emailExists(user.getEmail())) {
            throw new ConflictException(
                    "There is already an account with that email address: " + user.getEmail()
            );
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        emailService.sendWelcomeEmail(user.getEmail(), organizationService.getByIdIfExists(id));

        return userJpaRepository.save(user);
    }

    private boolean emailExists(String email) {
        return userJpaRepository.findByEmail(email)
                .isPresent();
    }
}

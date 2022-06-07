package com.alkemy.ong.domain.usecase.impl;

import com.alkemy.ong.common.exception.NotFoundException;
import com.alkemy.ong.domain.model.User;
import com.alkemy.ong.domain.model.UserList;
import com.alkemy.ong.domain.repository.UserRepository;
import com.alkemy.ong.domain.usecase.UserService;
import com.alkemy.ong.ports.input.rs.mapper.UserControllerMapperImpl;
import com.alkemy.ong.ports.input.rs.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userJpaRepository;

    private final UserControllerMapperImpl mapper;

    private final PasswordEncoder passwordEncoder;

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
        return new UserList(page.getContent(), pageRequest, page.getTotalElements());
    }

    @Override
    @Transactional
    public UserResponse updateUser(Long id, User entity) {

        /*User user = userJpaRepository.findById(id)
                .map(userJpa -> {
                    Optional.ofNullable(entity.getFirstName()).ifPresent(userJpa::setFirstName);
                    Optional.ofNullable(entity.getLastName()).ifPresent(userJpa::setLastName);
                    Optional.ofNullable(entity.getEmail()).ifPresent(userJpa::setEmail);
                    Optional.ofNullable(entity.getPassword()).ifPresent(userJpa::setPassword);
                    Optional.ofNullable(entity.getPhoto()).ifPresent(userJpa::setPhoto);
                    return userJpaRepository.save(userJpa);
                }).orElseThrow(() -> new NotFoundException(id));
        return mapper.userToUserResponse(user);*/

        try {
            User userToUpdate = userJpaRepository.getById(id);

            if (entity.getFirstName() != null) {
                userToUpdate.setFirstName(entity.getFirstName());
            }
            if (entity.getLastName() != null) {
                userToUpdate.setLastName(entity.getLastName());
            }
            if (entity.getEmail() != null) {
                userToUpdate.setEmail(entity.getEmail());
            }
            if (entity.getPassword() != null) {
                userToUpdate.setPassword(passwordEncoder.encode(entity.getPassword()));
            }
            if (entity.getPhoto() != null) {
                userToUpdate.setPhoto(entity.getPhoto());
            }
            userToUpdate.getAudit().setUpdatedAt(LocalDateTime.now());

            userJpaRepository.save(userToUpdate);
            return mapper.userToUserResponse(userToUpdate);

        } catch (Exception exception) {
            throw new NotFoundException(id);
        }
    }
}

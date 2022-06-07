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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userJpaRepository;

    private final UserControllerMapperImpl mapper;

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
    public UserResponse updateUser(Long id, User user) {
        try {
            User userToSave = userJpaRepository.getById(id);

            userToSave.setFirstName(user.getFirstName());
            userToSave.setLastName(user.getLastName());
            userToSave.setEmail(user.getEmail());
            userToSave.setPassword(user.getPassword());
            userToSave.setPhoto(user.getPhoto());

            userJpaRepository.save(userToSave);
            return mapper.userToUserResponse(userToSave);
        } catch (Exception exception) {
            throw new NotFoundException(id);
        }
    }
}

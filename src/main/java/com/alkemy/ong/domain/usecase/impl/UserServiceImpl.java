package com.alkemy.ong.domain.usecase.impl;

import com.alkemy.ong.common.exception.ConflictException;
import com.alkemy.ong.common.exception.NotFoundException;
import com.alkemy.ong.domain.model.Role;
import com.alkemy.ong.domain.model.User;
import com.alkemy.ong.domain.model.UserList;
import com.alkemy.ong.domain.repository.RoleRepository;
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

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userJpaRepository;
    private final EmailService emailService;
    private final OrganizationService organizationService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${default.organization.id}")
    private Long defaultOrganizationId;

    @Value("${default.role.id}")
    private Long defaultRoleId;

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
    public User updateUser(Long id, User entity) {

        Optional<User> userOptional = userJpaRepository.findById(id);
        if (userOptional.isPresent()) {
            User userToUpdate = userOptional.get();

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

            userJpaRepository.save(userToUpdate);
            return userToUpdate;
        } else {
            throw new NotFoundException(id);
        }
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        Optional<User> optional = userJpaRepository.findById(id);
        if (optional.isPresent()) {
            User user = optional.get();
            userJpaRepository.delete(user);
        }
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
        user.setRole(getRoleIfExists(defaultRoleId));

        emailService.sendWelcomeEmail(
                user.getEmail(), organizationService.getByIdIfExists(defaultOrganizationId));

        return userJpaRepository.save(user);
    }

    private Role getRoleIfExists(Long roleId) {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new NotFoundException(roleId));
    }

    private boolean emailExists(String email) {
        return userJpaRepository.findByEmail(email)
                .isPresent();
    }

}

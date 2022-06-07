package com.alkemy.ong.domain.repository;

import com.alkemy.ong.domain.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    Optional<User> findByEmail(String email);

    User getById(Long id);
}

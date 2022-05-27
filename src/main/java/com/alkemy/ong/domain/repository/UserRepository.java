package com.alkemy.ong.domain.repository;

import com.alkemy.ong.domain.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
}

package com.alkemy.ong.domain.repository;

import com.alkemy.ong.domain.model.Users;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UsersRepository extends PagingAndSortingRepository<Users, Long> {
}

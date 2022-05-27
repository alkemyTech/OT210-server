package com.alkemy.ong.domain.repository;

import com.alkemy.ong.domain.model.Role;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RoleRepository extends PagingAndSortingRepository<Role, Long> {
}

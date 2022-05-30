package com.alkemy.ong.domain.repository;

import com.alkemy.ong.domain.model.Member;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MemberRepository extends PagingAndSortingRepository<Member, Long> {
}

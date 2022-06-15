package com.alkemy.ong.domain.repository;

import com.alkemy.ong.domain.model.Slide;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SlideRepository extends PagingAndSortingRepository<Slide, Long> {

    @Query(nativeQuery = true, value = "SELECT max(order) FROM slide s")
    Integer getMaxOrder();
}

package com.alkemy.ong.domain.repository;

import com.alkemy.ong.domain.model.Slide;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface SlideRepository extends PagingAndSortingRepository<Slide, Long> {

    @Query(value = "select max(slide_order) from slide", nativeQuery = true)
    Optional<Integer> getMaxOrder();
}

package com.alkemy.ong.ports.input.rs.api;

import org.springframework.http.ResponseEntity;

public interface CategoryApi {

    ResponseEntity<Void> deleteCategory(Long id);

}

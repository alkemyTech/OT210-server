package com.alkemy.ong.ports.input.rs.api;


import com.alkemy.ong.ports.input.rs.request.SlideRequest;
import org.springframework.http.ResponseEntity;

public interface SlideApi {

    ResponseEntity<Void> createSlide(SlideRequest slideRequest, String fileName);
}

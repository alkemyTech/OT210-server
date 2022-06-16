package com.alkemy.ong.domain.usecase;


import com.alkemy.ong.domain.model.Slide;
import com.alkemy.ong.ports.input.rs.request.SlideRequest;


public interface SlideService {

    Slide getByIdIfExist(Long id);
    void deleteSlideByIdIfExist(Long id);
    void updateSlideIfExist( Long id, Slide slide);
}

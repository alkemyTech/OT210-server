package com.alkemy.ong.domain.usecase;


import com.alkemy.ong.domain.model.Slide;


public interface SlideService {

    Slide getByIdIfExist(Long id);
}

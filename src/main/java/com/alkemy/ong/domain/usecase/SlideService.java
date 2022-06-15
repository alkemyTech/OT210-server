package com.alkemy.ong.domain.usecase;


import com.alkemy.ong.domain.model.Slide;
public interface SlideService {

    Slide getByIdIfExist(Long id);
    void deleteSlideByIdIfExist(Long id);
    void createSlide(String imgBase64 , String text , Integer order, Long organizationId);
}

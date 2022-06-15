package com.alkemy.ong.domain.usecase;

import com.alkemy.ong.domain.model.Slide;

public interface SlideService {

    void createSlide(String imgBase64 , String text , Integer order, Long organizationId);
}

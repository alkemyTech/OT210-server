package com.alkemy.ong.domain.usecase;

import com.alkemy.ong.domain.model.Slide;

public interface SlideService {

    Long createSlide(Slide slide, String fileName);
}

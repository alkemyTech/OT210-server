package com.alkemy.ong.domain.usecase;

import com.alkemy.ong.domain.model.Slide;
import com.alkemy.ong.domain.model.SlideList;
import org.springframework.data.domain.PageRequest;

public interface SlideService {
    SlideList getList(PageRequest pageRequest);
    Slide getByIdIfExist(Long id);
    void deleteSlideByIdIfExist(Long id);
    void updateSlideIfExist( Long id, String imgBase64, String text, Integer order, Long organizationId);
    Long createSlide(String imgBase64 , String text , Integer order, Long organizationId);
}

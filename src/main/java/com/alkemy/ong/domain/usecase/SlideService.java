package com.alkemy.ong.domain.usecase;

import com.alkemy.ong.domain.model.SlideList;
import org.springframework.data.domain.PageRequest;
import com.alkemy.ong.domain.model.Slide;

public interface SlideService {

    SlideList getList(PageRequest pageRequest);
  
    Slide getByIdIfExist(Long id);

    void deleteSlideByIdIfExist(Long id);
  
}

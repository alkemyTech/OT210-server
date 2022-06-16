package com.alkemy.ong.domain.usecase;

import com.alkemy.ong.domain.model.SlideList;
import org.springframework.data.domain.PageRequest;

public interface SlideService {

    SlideList getList(PageRequest pageRequest);

}

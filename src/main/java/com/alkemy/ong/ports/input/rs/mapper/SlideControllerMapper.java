package com.alkemy.ong.ports.input.rs.mapper;

import com.alkemy.ong.domain.model.Slide;
import com.alkemy.ong.ports.input.rs.response.SlideResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface SlideControllerMapper  extends CommonMapper{

    @Mapping(target = "image", source = "imageUrl")
    @Mapping(target = "text", source = "text")
    @Mapping(target = "order",source = "order")
    @Mapping(target = "organizationResponse", source = "organization")
    SlideResponse slideToSlideResponse(Slide slide);
}

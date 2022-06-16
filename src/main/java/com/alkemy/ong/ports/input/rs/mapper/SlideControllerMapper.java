package com.alkemy.ong.ports.input.rs.mapper;

import com.alkemy.ong.domain.model.Slide;
import com.alkemy.ong.ports.input.rs.response.SlideResponse;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
@Mapper
public interface SlideControllerMapper extends CommonMapper{

    @Mapping(target = "image", source = "imageUrl")
    @Mapping(target = "text", source = "text")
    @Mapping(target = "order",source = "order")
    @Mapping(target = "organization", source = "organization")
  
    @IterableMapping(qualifiedByName = "slideToSlideResponse")
    List<SlideResponse> slideListToSlideResponseList(List<Slide> slides);

    @Named("slideToSlideResponse")
    SlideResponse slideToSlideResponse(Slide slide);
}

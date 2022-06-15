package com.alkemy.ong.ports.input.rs.mapper;

import com.alkemy.ong.domain.model.New;
import com.alkemy.ong.ports.input.rs.request.CreateNewRequest;
import com.alkemy.ong.ports.input.rs.response.NewResponse;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper
public interface NewControllerMapper extends CommonMapper {

    @IterableMapping(qualifiedByName = "newToNewResponse")
    List<NewResponse> newListToNewResponseList(List<New> news);

    @Named("newToNewResponse")
    NewResponse newToNewResponse(New news);

    New createNewRequestToNew(CreateNewRequest create);
}

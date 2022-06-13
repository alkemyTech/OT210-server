package com.alkemy.ong.ports.input.rs.mapper;

import com.alkemy.ong.domain.model.New;
import com.alkemy.ong.ports.input.rs.request.CreateNewRequest;
import org.mapstruct.Mapper;

@Mapper
public interface NewControllerMapper extends CommonMapper {

    New createNewRequestToNew(CreateNewRequest create);
}

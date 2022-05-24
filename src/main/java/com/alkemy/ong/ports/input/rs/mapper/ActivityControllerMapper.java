package com.alkemy.ong.ports.input.rs.mapper;

import com.alkemy.ong.domain.model.Activity;
import com.alkemy.ong.domain.model.Alkymer;
import com.alkemy.ong.ports.input.rs.request.CreateActivityRequest;
import com.alkemy.ong.ports.input.rs.request.CreateAlkymerRequest;
import com.alkemy.ong.ports.input.rs.request.UpdateAlkymerRequest;
import com.alkemy.ong.ports.input.rs.response.AlkymerResponse;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper
public interface ActivityControllerMapper extends CommonMapper {
    Activity createActivityRequestToActivity(CreateActivityRequest activity);
}

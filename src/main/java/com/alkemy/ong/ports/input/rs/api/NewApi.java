package com.alkemy.ong.ports.input.rs.api;

import com.alkemy.ong.ports.input.rs.request.CreateNewRequest;
import com.alkemy.ong.ports.input.rs.response.NewResponseList;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Optional;

@Validated
public interface NewApi {

    ResponseEntity<Void> createNew(@Valid CreateNewRequest createNewRequest);

    ResponseEntity<NewResponseList> getNews(Optional<Integer> page, Optional<Integer> size);
}

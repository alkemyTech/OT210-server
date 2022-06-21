package com.alkemy.ong.ports.input.rs.api;

import com.alkemy.ong.ports.input.rs.request.CreateNewRequest;
import com.alkemy.ong.ports.input.rs.response.CommentResponseList;
import com.alkemy.ong.ports.input.rs.response.NewResponse;
import com.alkemy.ong.ports.input.rs.response.NewResponseList;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Validated
public interface NewApi {

    ResponseEntity<Void> createNew(@Valid CreateNewRequest createNewRequest);

    ResponseEntity<NewResponseList> getNews(Optional<Integer> page, Optional<Integer> size);

    void deleteNew(@NotNull Long id);

    ResponseEntity<NewResponse> updateNew(@NotNull @PathVariable("id") Long id,
                                          @Valid @RequestBody CreateNewRequest createNewRequest);

    ResponseEntity<CommentResponseList> getCommentsFromNew(@NotNull Long idNew, Optional<Integer> page, Optional<Integer> size);
}

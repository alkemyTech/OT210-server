package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.domain.model.CommentList;
import com.alkemy.ong.domain.model.New;
import com.alkemy.ong.domain.model.NewList;
import com.alkemy.ong.domain.usecase.NewService;
import com.alkemy.ong.ports.input.rs.api.ApiConstants;
import com.alkemy.ong.ports.input.rs.api.NewApi;
import com.alkemy.ong.ports.input.rs.mapper.CommentControllerMapper;
import com.alkemy.ong.ports.input.rs.mapper.NewControllerMapper;
import com.alkemy.ong.ports.input.rs.request.CreateNewRequest;
import com.alkemy.ong.ports.input.rs.response.CommentResponse;
import com.alkemy.ong.ports.input.rs.response.CommentResponseList;
import com.alkemy.ong.ports.input.rs.response.NewResponse;
import com.alkemy.ong.ports.input.rs.response.NewResponseList;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static com.alkemy.ong.ports.input.rs.api.ApiConstants.NEWS_URI;

@RestController
@RequestMapping(NEWS_URI)
@RequiredArgsConstructor
public class NewController implements NewApi {

    private final NewService service;
    private final NewControllerMapper mapper;
    private final CommentControllerMapper commmentMapper;

    @Override
    @PostMapping
    public ResponseEntity<Void> createNew(@Valid @RequestBody CreateNewRequest createNewRequest) {
        New news = mapper.createNewRequestToNew(createNewRequest);

        final long id = service.createNew(news);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(id)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @Override
    @GetMapping
    public ResponseEntity<NewResponseList> getNews(Optional<Integer> page, Optional<Integer> size) {
        final int pageNumber = page.filter(p -> p > 0).orElse(ApiConstants.DEFAULT_PAGE);
        final int pageSize = size.filter(s -> s > 0).orElse(ApiConstants.DEFAULT_PAGE_SIZE);

        NewList list = service.getList(PageRequest.of(pageNumber, pageSize));
        NewResponseList response;
        {
            response = new NewResponseList();
            List<NewResponse> content = mapper.newListToNewResponseList(list.getContent());
            response.setContent(content);
            final int nextPage = list.getPageable().next().getPageNumber();
            response.setNextUri(ApiConstants.uriByPageAsString.apply(nextPage));

            final int previousPage = list.getPageable().previousOrFirst().getPageNumber();
            response.setPreviousUri(ApiConstants.uriByPageAsString.apply(previousPage));

            response.setTotalPages(list.getTotalPages());
            response.setTotalElements(list.getTotalElements());
        }
        return ResponseEntity.ok().body(response);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNew(@NotNull @PathVariable Long id) {
        service.deleteById(id);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<NewResponse> updateNew(@NotNull @PathVariable Long id, @Valid @RequestBody CreateNewRequest createNewRequest) {
        New news = mapper.createNewRequestToNew(createNewRequest);
        New newToUpdate = service.updateNew(id,news);
        NewResponse response = mapper.newToNewResponse(newToUpdate);
        return ResponseEntity.ok().body(response);
    }

    @Override
    @GetMapping("{id}/comments")
    public ResponseEntity<CommentResponseList> getCommentsFromNew(@NotNull @PathVariable Long idNew, Optional<Integer> page, Optional<Integer> size) {

        final int pageNumber = page.filter(p -> p > 0).orElse(ApiConstants.DEFAULT_PAGE);
        final int pageSize = size.filter(s -> s > 0).orElse(ApiConstants.DEFAULT_PAGE_SIZE);

        CommentList commentList = service.getCommentsFromNew(idNew, PageRequest.of(pageNumber, pageSize));
        CommentResponseList response;
        {
            response = new CommentResponseList();
            List<CommentResponse> content = commmentMapper.commentListToCommentResponseList(commentList.getContent());
            response.setContent(content);

            final int nextPage = commentList.getPageable().next().getPageNumber();
            response.setNextUri(ApiConstants.uriByPageAsString.apply(nextPage));

            final int previousPage = commentList.getPageable().previousOrFirst().getPageNumber();
            response.setPreviousUri(ApiConstants.uriByPageAsString.apply(previousPage));

            response.setTotalPages(commentList.getTotalPages());
            response.setTotalElements(commentList.getTotalElements());
        }
        return ResponseEntity.ok().body(response);

    }

}

package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.domain.model.CommentList;
import com.alkemy.ong.domain.usecase.CommentService;
import com.alkemy.ong.ports.input.rs.api.ApiConstants;
import com.alkemy.ong.ports.input.rs.api.CommentApi;
import com.alkemy.ong.ports.input.rs.mapper.CommentControllerMapper;
import com.alkemy.ong.ports.input.rs.response.CommentResponse;
import com.alkemy.ong.ports.input.rs.response.CommentResponseList;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(ApiConstants.COMMENTS_URI)
@RequiredArgsConstructor
public class CommentController implements CommentApi {

    private final CommentService commentService;
    private final CommentControllerMapper mapper;

    @Override
    @GetMapping
    public ResponseEntity<CommentResponseList> getComments(@RequestParam Optional<Integer> page,
                                                           @RequestParam Optional<Integer> size) {

        final int pageNumber = page.filter(p -> p > 0).orElse(ApiConstants.DEFAULT_PAGE);
        final int pageSize = size.filter(s -> s > 0).orElse(ApiConstants.DEFAULT_PAGE_SIZE);

        CommentList commentList = commentService.getComments(PageRequest.of(pageNumber, pageSize));

        CommentResponseList response;
        {
            response = new CommentResponseList();

            List<CommentResponse> content = mapper.commentListToCommentResponseList(commentList.getContent());
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

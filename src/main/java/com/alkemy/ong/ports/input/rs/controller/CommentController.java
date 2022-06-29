package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.domain.model.Comment;
import com.alkemy.ong.domain.model.CommentList;
import com.alkemy.ong.domain.model.User;
import com.alkemy.ong.domain.usecase.CommentService;
import com.alkemy.ong.ports.input.rs.api.ApiConstants;
import com.alkemy.ong.ports.input.rs.api.CommentApi;
import com.alkemy.ong.ports.input.rs.mapper.CommentControllerMapper;
import com.alkemy.ong.ports.input.rs.request.CreateCommentRequest;
import com.alkemy.ong.ports.input.rs.response.CommentResponse;
import com.alkemy.ong.ports.input.rs.response.CommentResponseList;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static com.alkemy.ong.ports.input.rs.api.ApiConstants.COMMENTS_URI;

@RestController
@RequestMapping(COMMENTS_URI)
@RequiredArgsConstructor
public class CommentController implements CommentApi {

    private final CommentService service;
    private final CommentControllerMapper mapper;

    @Override
    @PostMapping
    public ResponseEntity<Void> createComment(@Valid @RequestBody CreateCommentRequest request,
                                              @AuthenticationPrincipal User user) {

        Comment comment = mapper.createCommentRequestToComment(request, user);
        final long id = service.createEntity(comment, request.getNewId());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(id)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<CommentResponseList> getComments(@RequestParam Optional<Integer> page,
                                                           @RequestParam Optional<Integer> size) {

        final int pageNumber = page.filter(p -> p > 0).orElse(ApiConstants.DEFAULT_PAGE);
        final int pageSize = size.filter(s -> s > 0).orElse(ApiConstants.DEFAULT_PAGE_SIZE);

        CommentList commentList = service.getComments(PageRequest.of(pageNumber, pageSize));

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


    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@NotNull @PathVariable Long id, @AuthenticationPrincipal User user) {
        service.deleteById(id, user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.domain.model.Contact;
import com.alkemy.ong.domain.model.ContactList;
import com.alkemy.ong.domain.usecase.ContactService;
import com.alkemy.ong.ports.input.rs.api.ApiConstants;
import com.alkemy.ong.ports.input.rs.api.ContactApi;
import com.alkemy.ong.ports.input.rs.mapper.ContactControllerMapper;
import com.alkemy.ong.ports.input.rs.request.CreateContactRequest;
import com.alkemy.ong.ports.input.rs.response.ContactResponse;
import com.alkemy.ong.ports.input.rs.response.ContactResponseList;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static com.alkemy.ong.ports.input.rs.api.ApiConstants.CONTACTS_URI;

@RestController
@RequestMapping(CONTACTS_URI)
@RequiredArgsConstructor
public class ContactController  implements ContactApi {

    private final ContactService service;
    private final ContactControllerMapper mapper;

    @Override
    @PostMapping
    public ResponseEntity<Void> createContact(@Valid @RequestBody CreateContactRequest createContactRequest) {

        Contact contact = mapper.createContactRequestToContact(createContactRequest);
        final long id = service.createEntity(contact);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(id)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @Override
    @GetMapping
    public ResponseEntity<ContactResponseList> getContacts(@RequestParam Optional<Integer> page,
                                                           @RequestParam Optional<Integer> size) {

        final int pageNumber = page.filter(p -> p > 0).orElse(ApiConstants.DEFAULT_PAGE);
        final int pageSize = size.filter(s -> s > 0).orElse(ApiConstants.DEFAULT_PAGE_SIZE);

        ContactList list = service.getList(PageRequest.of(pageNumber, pageSize));

        ContactResponseList response;
        {
            response = new ContactResponseList();

            List<ContactResponse> content = mapper.contactListToContactResponseList(list.getContent());
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
}

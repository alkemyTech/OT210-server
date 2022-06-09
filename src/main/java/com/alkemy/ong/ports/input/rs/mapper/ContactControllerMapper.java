package com.alkemy.ong.ports.input.rs.mapper;

import com.alkemy.ong.domain.model.Contact;
import com.alkemy.ong.ports.input.rs.request.CreateContactRequest;
import com.alkemy.ong.ports.input.rs.response.ContactResponse;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper
public interface ContactControllerMapper extends CommonMapper{

    Contact createContactRequestToContact(CreateContactRequest create);

    @IterableMapping(qualifiedByName = "contactToContactResponse")
    List<ContactResponse> contactListToContactResponseList(List<Contact> contacts);

    @Named("contactToContactResponse")
    ContactResponse contactToContactResponse(Contact contact);
}

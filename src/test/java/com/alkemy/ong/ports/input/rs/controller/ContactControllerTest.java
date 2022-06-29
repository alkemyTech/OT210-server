package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.common.exception.handler.GlobalExceptionHandler;
import com.alkemy.ong.common.util.JsonUtils;
import com.alkemy.ong.domain.model.Contact;
import com.alkemy.ong.domain.model.ContactList;
import com.alkemy.ong.domain.usecase.ContactService;
import com.alkemy.ong.ports.input.rs.api.ApiConstants;
import com.alkemy.ong.ports.input.rs.mapper.ContactControllerMapper;
import com.alkemy.ong.ports.input.rs.request.CreateContactRequest;
import com.alkemy.ong.ports.input.rs.response.ContactResponseList;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ContactControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    ContactController controller;

    @Mock
    ContactService service;

    @Spy
    ContactControllerMapper mapper = Mappers.getMapper(ContactControllerMapper.class);

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(GlobalExceptionHandler.class)
                .build();
    }

    @Test
    void createContact_shouldReturn201() throws Exception {

        CreateContactRequest contactRequest = CreateContactRequest.builder()
                .name("Luis")
                .email("luisFarrel@gmail.com")
                .phone("2215983565")
                .message("hello luis")
                .build();

        given(service.createEntity(any(Contact.class))).willReturn(1L);

        final String actualLocation = mockMvc.perform(post(ApiConstants.CONTACTS_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.objectToJson(contactRequest)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getHeader(HttpHeaders.LOCATION);

        AssertionsForClassTypes.assertThat(actualLocation).isEqualTo("http://localhost/v1/contacts/1");
    }

    @Test
    void getContacts_ShouldReturn200() throws Exception {

        Contact contact = new Contact();
        contact.setId(2L);
        contact.setName("Jhonatan");
        contact.setEmail("Joestar@gmail.com");
        contact.setMessage("Hello Jhonatan Joestar");
        contact.setPhone("2216952285");

        ContactList contacts = new ContactList(List.of(contact), Pageable.ofSize(1),1);

        given(service.getList(any(PageRequest.class))).willReturn(contacts);

        String content = mockMvc.perform(get(ApiConstants.CONTACTS_URI + "?page=0&size=1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(content).isNotBlank();

        ContactResponseList response = JsonUtils.jsonToObject(content, ContactResponseList.class);

        assertThat(response.getTotalElements()).isEqualTo(1);
        assertThat(response.getTotalPages()).isEqualTo(1);
        assertThat(response.getNextUri()).isEqualTo("http://localhost/v1/contacts?size=1&page=1");
        assertThat(response.getPreviousUri()).isEqualTo("http://localhost/v1/contacts?size=1&page=0");
        assertThat(response.getContent()).isNotEmpty();
    }
}

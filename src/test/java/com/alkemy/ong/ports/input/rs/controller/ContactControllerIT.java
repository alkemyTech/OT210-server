package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.H2Config;
import com.alkemy.ong.common.util.JsonUtils;
import com.alkemy.ong.domain.model.ContactList;
import com.alkemy.ong.ports.input.rs.api.ApiConstants;
import com.alkemy.ong.ports.input.rs.request.CreateCategoryRequest;
import com.alkemy.ong.ports.input.rs.request.CreateContactRequest;
import com.alkemy.ong.ports.input.rs.response.ContactResponseList;
import com.alkemy.ong.ports.input.rs.response.TestimonialResponseList;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringJUnitConfig(classes = H2Config.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ContactControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Test
    @Order(1)
    @WithUserDetails("admin@somosmas.org")
    void createContact_shouldReturn201()throws Exception{

        CreateContactRequest contactRequest = CreateContactRequest.builder()
                .name("Luis")
                .email("luisFarrel@gmail.com")
                .phone("2215983565")
                .message("hello luis")
                .build();

        final String actualLocation = mockMvc.perform(post(ApiConstants.CONTACTS_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.objectToJson(contactRequest)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getHeader(HttpHeaders.LOCATION);

        assertThat(actualLocation).isEqualTo("http://localhost/v1/contacts/1");
    }

    @Test
    @Order(2)
    @WithUserDetails("admin@somosmas.org")
    void getContacts_shouldReturn200() throws Exception {

        String content = mockMvc.perform(get(ApiConstants.CONTACTS_URI))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Assertions.assertThat(content).isNotBlank();

        ContactResponseList response = JsonUtils.jsonToObject(content,ContactResponseList.class );

        Assertions.assertThat(response.getTotalElements()).isEqualTo(1);
        Assertions.assertThat(response.getTotalPages()).isEqualTo(1);
        Assertions.assertThat(response.getNextUri()).isEqualTo("http://localhost/v1/contacts?page=1");
        Assertions.assertThat(response.getPreviousUri()).isEqualTo("http://localhost/v1/contacts?page=0");
        Assertions.assertThat(response.getContent()).isNotEmpty();
    }

    @Test
    @Order(3)
    @WithAnonymousUser
    void createContact_shouldReturn400()throws Exception{

        CreateContactRequest contactRequest = CreateContactRequest.builder()
                .name("Luis")
                .email("")
                .phone("2215983565")
                .message("hello luis")
                .build();

        final String actualLocation = mockMvc.perform(post(ApiConstants.CONTACTS_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.objectToJson(contactRequest)))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getHeader(HttpHeaders.LOCATION);
    }



    @Test
    @Order(4)
    @WithAnonymousUser
    void getContacts_shouldReturn401() throws Exception {

        mockMvc.perform(get(ApiConstants.CONTACTS_URI))
                .andExpect(status().isUnauthorized())
                .andDo(print());

    }

    @Test
    @Order(5)
    @WithUserDetails("admin@somosmas.org")
    void getContacts_shouldReturn404() throws Exception {

        mockMvc.perform(get(ApiConstants.CONTACTS_URI + "/2"))
                .andExpect(status().isNotFound())
                .andDo(print());

    }
}

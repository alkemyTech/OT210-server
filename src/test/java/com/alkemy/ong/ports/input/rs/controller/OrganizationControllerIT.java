package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.H2Config;
import com.alkemy.ong.common.util.JsonUtils;
import com.alkemy.ong.ports.input.rs.api.ApiConstants;
import com.alkemy.ong.ports.input.rs.request.UpdateOrganizationRequest;
import com.alkemy.ong.ports.input.rs.response.OrganizationResponse;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringJUnitConfig(classes = H2Config.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrganizationControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Test
    @Order(1)
    @WithUserDetails("admin@somosmas.org")
    void getOrganization_shouldReturn200() throws Exception {

        String content = mockMvc.perform(get(ApiConstants.ORGANIZATIONS_URI + "/1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(content).isNotBlank();

        OrganizationResponse response = JsonUtils.jsonToObject(content, OrganizationResponse.class);
        assertThat(content).isNotBlank();
        assertThat(response.getId()).isEqualTo(99);
        assertThat(response.getName()).isEqualTo("Name Organization");
        assertThat(response.getImage()).isEqualTo("image_organization.jpg");
        assertThat(response.getAddress()).isEqualTo("Organization address 123");
        assertThat(response.getPhone()).isEqualTo(123456789);
        assertThat(response.getEmail()).isEqualTo("organization@organization.com");
        assertThat(response.getFacebookContact()).isEqualTo("http://facebook.com/Organization");
        assertThat(response.getLinkedinContact()).isEqualTo("http://linkedIn.com/Organization");
        assertThat(response.getInstagramContact()).isEqualTo("http://instagram.com/Organization");
    }

    @Test
    @Order(2)
    @WithUserDetails("admin@somosmas.org")
    void updateOrganization_shouldReturn200() throws Exception {

        UpdateOrganizationRequest request = UpdateOrganizationRequest.builder()
                .name("Name Organization")
                .image("image_organization.jpg")
                .address("Organization address 123")
                .phone(123456789)
                .build();

        mockMvc.perform(patch(ApiConstants.ORGANIZATIONS_URI + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.objectToJson(request)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Order(3)
    @WithAnonymousUser
    void getOrganization_shouldReturn401() throws Exception {
        mockMvc.perform(get(ApiConstants.ORGANIZATIONS_URI + "/1"))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }
}
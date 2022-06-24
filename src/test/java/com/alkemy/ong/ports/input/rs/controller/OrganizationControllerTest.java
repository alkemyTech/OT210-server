package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.common.exception.handler.GlobalExceptionHandler;
import com.alkemy.ong.common.util.JsonUtils;
import com.alkemy.ong.domain.model.Organization;
import com.alkemy.ong.domain.usecase.OrganizationService;
import com.alkemy.ong.ports.input.rs.api.ApiConstants;
import com.alkemy.ong.ports.input.rs.mapper.OrganizationControllerMapper;
import com.alkemy.ong.ports.input.rs.request.UpdateOrganizationRequest;
import com.alkemy.ong.ports.input.rs.response.OrganizationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class OrganizationControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    OrganizationController controller;
    @Mock
    OrganizationService service;

    @Spy
    OrganizationControllerMapper mapper = Mappers.getMapper(OrganizationControllerMapper.class);

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(GlobalExceptionHandler.class)
                .build();
    }

    @Test
    void getOrganization_shouldReturn200() throws Exception {

        Organization organization = new Organization();
        organization.setId(99L);
        organization.setName("Name Organization");
        organization.setImage("image_organization.jpg");
        organization.setAddress("Organization address 123");
        organization.setPhone(123456789);
        organization.setEmail("organization@organization.com");
        organization.setWelcomeText("Welcome Organization");
        organization.setAboutUsText("About Organization");
        organization.setFacebookContact("http://facebook.com/Organization");
        organization.setLinkedinContact("http://linkedIn.com/Organization");
        organization.setInstagramContact("http://instagram.com/Organization");

        given(service.getByIdIfExists(99L)).willReturn(organization);

        String content = mockMvc.perform(get(ApiConstants.ORGANIZATIONS_URI + "/99"))
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
    void updateOrganization_shouldReturn200() throws Exception {
        UpdateOrganizationRequest request = UpdateOrganizationRequest.builder()
                .name("Name Organization")
                .image("image_organization.jpg")
                .address("Organization address 123")
                .phone(123456789)
                .build();

        mockMvc.perform(patch(ApiConstants.ORGANIZATIONS_URI + "/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.objectToJson(request)))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

        String content = mockMvc.perform(get(ApiConstants.ORGANIZATIONS_URI + "/public" + "/1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(content).isNotBlank();

        OrganizationResponse response = JsonUtils.jsonToObject(content, OrganizationResponse.class);
        assertThat(content).isNotBlank();
        assertThat(response.getId()).isEqualTo(1);
        assertThat(response.getName()).isEqualTo("Somos Mas");
        assertThat(response.getImage()).isEqualTo("https://cohorte-abril-98a56bb4.s3.amazonaws.com/1653997314766-LOGO-SOMOS_MAS.png");
        assertThat(response.getAddress()).isEqualTo("Barrio La Cava");
        assertThat(response.getPhone()).isEqualTo(1160112988);
        assertThat(response.getEmail()).isEqualTo("somosmasong4@gmail.com");
    }

    @Test
    @Order(2)
    @WithUserDetails("admin@somosmas.org")
    void updateOrganization_shouldReturn200() throws Exception {

        UpdateOrganizationRequest request = UpdateOrganizationRequest.builder()
                .name("Updated Somos Más")
                .image("https://cohorte-abril-98a56bb4.s3.amazonaws.com/1653997314766-LOGO-SOMOS_MAS2.png")
                .address("Updated Barrio La Cava")
                .phone(123456789)
                .welcomeText("""
                            Trabajar articuladamente con los distintos aspectos de la vida de las familias,
                            generando espacios de desarrollo personal y familiar, brindando herramientas que
                            logren mejorar la calidad de vida a través de su propio esfuerzo.
                            """)
                .build();

        mockMvc.perform(put(ApiConstants.ORGANIZATIONS_URI + "/public" + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.objectToJson(request)))
                .andExpect(status().isNoContent())
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

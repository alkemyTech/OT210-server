package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.H2Config;
import com.alkemy.ong.common.util.JsonUtils;
import com.alkemy.ong.domain.model.New;
import com.alkemy.ong.ports.input.rs.api.ApiConstants;
import com.alkemy.ong.ports.input.rs.request.CreateNewRequest;
import com.alkemy.ong.ports.input.rs.request.TestimonialRequest;
import com.alkemy.ong.ports.input.rs.response.AlkymerResponseList;
import com.alkemy.ong.ports.input.rs.response.NewResponse;
import com.alkemy.ong.ports.input.rs.response.NewResponseList;
import com.alkemy.ong.ports.input.rs.response.TestimonialResponse;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringJUnitConfig(classes = H2Config.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class NewControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Test
    @Order(1)
    @WithUserDetails("admin@somosmas.org")
    void createNew_shouldReturn201() throws Exception {

        CreateNewRequest request = CreateNewRequest.builder()
                .name("noticia")
                .content("contenido")
                .image("image.png")
                .build();
        final String actualLocation = mockMvc.perform(post(ApiConstants.NEWS_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.objectToJson(request)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getHeader(HttpHeaders.LOCATION);

        assertThat(actualLocation).isEqualTo("http://localhost/v1/new/99");
    }

    @Test
    @Order(2)
    @WithUserDetails("admin@somosmas.org")
    void getNews_shouldReturn200() throws Exception {

        String content = mockMvc.perform(get(ApiConstants.NEWS_URI))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(content).isNotBlank();

        NewResponseList response = JsonUtils.jsonToObject(content, NewResponseList.class);

        assertThat(response.getTotalElements()).isEqualTo(1);
        assertThat(response.getTotalPages()).isEqualTo(1);
        assertThat(response.getNextUri()).isEqualTo("http://localhost/v1/news?page=1");
        assertThat(response.getPreviousUri()).isEqualTo("http://localhost/v1/news?page=0");
        assertThat(response.getContent()).isNotEmpty();

    }

    @Test
    @Order(3)
    @WithUserDetails("admin@somosmas.org")
    void updateNew_shouldReturn200() throws Exception {
        CreateNewRequest update = CreateNewRequest.builder()
                .name("Name UPDATED")
                .content("Content UPDATED")
                .image("Image UPDATED")
                .build();

        String content = mockMvc.perform(put(ApiConstants.NEWS_URI + "/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.objectToJson(update)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(content).isNotBlank();

        NewResponse response = JsonUtils.jsonToObject(content, NewResponse.class);

        assertThat(response.getName()).isEqualTo("Name UPDATED");
        assertThat(response.getContent()).isEqualTo("Content UPDATED");
        assertThat(response.getImage()).isEqualTo("Image UPDATED");
    }

    @Test
    @Order(4)
    @WithUserDetails("admin@somosmas.org")
    void deleteNew_shouldReturn204() throws Exception {
        mockMvc.perform(delete(ApiConstants.NEWS_URI + "/1"))
                .andExpect(status().isNoContent())
                .andDo(print());
    }
    @Test
    @Order(5)
    @WithUserDetails("jdoe@somosmas.org")
    void deleteNew_shouldReturn403() throws Exception {
        mockMvc.perform(delete(ApiConstants.NEWS_URI + "/1"))
                .andExpect(status().isNoContent())
                .andDo(print());
    }


}
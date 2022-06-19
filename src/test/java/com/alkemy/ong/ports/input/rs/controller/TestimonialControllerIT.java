package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.H2Config;
import com.alkemy.ong.common.util.JsonUtils;
import com.alkemy.ong.ports.input.rs.api.ApiConstants;
import com.alkemy.ong.ports.input.rs.request.CreateTestimonialRequest;
import com.alkemy.ong.ports.input.rs.request.TestimonialRequest;
import com.alkemy.ong.ports.input.rs.response.TestimonialResponse;
import com.alkemy.ong.ports.input.rs.response.TestimonialResponseList;
import lombok.With;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringJUnitConfig(classes = H2Config.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestimonialControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Test
    @Order(1)
    @WithUserDetails("admin@somosmas.org")
    void createTestimonial_shouldReturn201() throws Exception {

        CreateTestimonialRequest request = CreateTestimonialRequest.builder()
                .name("Name Testimonial")
                .image("Image Testimonial")
                .content("Content Testimonial")
                .build();

        final String actualLocation = mockMvc.perform(post(ApiConstants.TESTIMONIALS_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.objectToJson(request)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getHeader(HttpHeaders.LOCATION);

        assertThat(actualLocation).isEqualTo("http://localhost/v1/testimonials/1");

    }

    @Test
    @Order(2)
    @WithUserDetails("jdoe@somosmas.org")
    void getTestimonials_shouldReturn200() throws Exception {

        String content = mockMvc.perform(get(ApiConstants.TESTIMONIALS_URI))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(content).isNotBlank();

        TestimonialResponseList response = JsonUtils.jsonToObject(content, TestimonialResponseList.class);

        assertThat(response.getTotalElements()).isEqualTo(1);
        assertThat(response.getTotalPages()).isEqualTo(1);
        assertThat(response.getNextUri()).isEqualTo("http://localhost/v1/testimonials?page=1");
        assertThat(response.getPreviousUri()).isEqualTo("http://localhost/v1/testimonials?page=0");
        assertThat(response.getContent()).isNotEmpty();
    }

    @Test
    @Order(3)
    @WithAnonymousUser
    void getTestimonials_shouldReturn401() throws Exception {
        mockMvc.perform(get(ApiConstants.TESTIMONIALS_URI))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }


    @Test
    @Order(4)
    @WithUserDetails("admin@somosmas.org")
    void updateTestimonial_shouldReturn200() throws Exception {

        TestimonialRequest update = TestimonialRequest.builder()
                .name("Name UPDATED")
                .content("Content UPDATED")
                .image("Image UPDATED")
                .build();

        String content = mockMvc.perform(put(ApiConstants.TESTIMONIALS_URI + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.objectToJson(update)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(content).isNotBlank();

        TestimonialResponse response = JsonUtils.jsonToObject(content, TestimonialResponse.class);

        assertThat(response.getName()).isEqualTo("Name UPDATED");
        assertThat(response.getContent()).isEqualTo("Content UPDATED");
        assertThat(response.getImage()).isEqualTo("Image UPDATED");
    }

    @Test
    @Order(5)
    @WithUserDetails("admin@somosmas.org")
    void updateTestimonial_shouldReturn404() throws Exception{
        TestimonialRequest update = TestimonialRequest.builder()
                .name("Name UPDATED")
                .content("Content UPDATED")
                .image("Image UPDATED")
                .build();

        mockMvc.perform(put(ApiConstants.TESTIMONIALS_URI + "/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.objectToJson(update)))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @Order(6)
    @WithUserDetails("jdoe@somosmas.org")
    void deleteTestimonial_shouldReturn403() throws Exception {
        mockMvc.perform(delete(ApiConstants.TESTIMONIALS_URI + "/1"))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @Test
    @Order(7)
    @WithUserDetails("admin@somosmas.org")
    void deleteTestimonial_shouldReturn204() throws Exception {
        mockMvc.perform(delete(ApiConstants.TESTIMONIALS_URI +"/1"))
                .andExpect(status().isNoContent())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

}

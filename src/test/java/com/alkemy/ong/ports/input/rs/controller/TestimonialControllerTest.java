package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.common.exception.handler.GlobalExceptionHandler;
import com.alkemy.ong.common.util.JsonUtils;
import com.alkemy.ong.domain.model.Testimonial;
import com.alkemy.ong.domain.usecase.TestimonialService;
import com.alkemy.ong.ports.input.rs.api.ApiConstants;
import com.alkemy.ong.ports.input.rs.mapper.TestimonialControllerMapper;
import com.alkemy.ong.ports.input.rs.request.CreateTestimonialRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class TestimonialControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    TestimonialController controller;
    @Mock
    TestimonialService service;
    @Spy
    TestimonialControllerMapper mapper = Mappers.getMapper(TestimonialControllerMapper.class);

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(GlobalExceptionHandler.class)
                .build();
    }

    @Test
    void createTestimonial_shoulReturn201() throws Exception{

        CreateTestimonialRequest request = CreateTestimonialRequest.builder()
                .name("TestimonialName")
                .content("TestimonialContent")
                .image("TestimonialImage")
                .build();

        given(service.createEntity(any(Testimonial.class))).willReturn(99L);

        final String actualLocation = mockMvc.perform(post(ApiConstants.TESTIMONIALS_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.objectToJson(request)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getHeader(HttpHeaders.LOCATION);

        assertThat(actualLocation).isEqualTo("http://localhost/v1/testimonials/99");
    }

    @Test
    void getTestimonial_shouldReturn200() throws Exception {

        Testimonial testimonial = new Testimonial();
        testimonial.setName("TestimonialName");
        testimonial.setContent("TestimonialContent");
        testimonial.setImage("TestimonialImage");
        testimonial.setId(99L);

//        given(service.get)
    }
}

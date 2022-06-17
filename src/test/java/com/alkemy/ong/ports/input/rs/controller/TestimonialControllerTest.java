package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.common.exception.handler.GlobalExceptionHandler;
import com.alkemy.ong.common.util.JsonUtils;
import com.alkemy.ong.domain.model.Testimonial;
import com.alkemy.ong.domain.model.TestimonialList;
import com.alkemy.ong.domain.usecase.TestimonialService;
import com.alkemy.ong.ports.input.rs.api.ApiConstants;
import com.alkemy.ong.ports.input.rs.mapper.TestimonialControllerMapper;
import com.alkemy.ong.ports.input.rs.request.CreateTestimonialRequest;
import com.alkemy.ong.ports.input.rs.request.TestimonialRequest;
import com.alkemy.ong.ports.input.rs.response.AlkymerResponseList;
import com.alkemy.ong.ports.input.rs.response.TestimonialResponse;
import com.alkemy.ong.ports.input.rs.response.TestimonialResponseList;
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

import static com.fasterxml.jackson.databind.util.ClassUtil.name;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
    void getTestimonials_shouldReturn200() throws Exception {

        Testimonial testimonial = new Testimonial();
        testimonial.setId(99L);
        testimonial.setName("TestimonialName");
        testimonial.setContent("TestimonialContent");
        testimonial.setImage("TestimonialImage");

        TestimonialList list = new TestimonialList(List.of(testimonial), Pageable.ofSize(1), 1);

        given(service.getList(any(PageRequest.class))).willReturn(list);

        String content = mockMvc.perform(get(ApiConstants.TESTIMONIALS_URI + "?page=0&size=1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(content).isNotBlank();

        TestimonialResponseList response = JsonUtils.jsonToObject(content, TestimonialResponseList.class);

        assertThat(response.getTotalElements()).isEqualTo(1);
        assertThat(response.getTotalPages()).isEqualTo(1);
        assertThat(response.getNextUri()).isEqualTo("http://localhost/v1/testimonials?size=1&page=1");
        assertThat(response.getPreviousUri()).isEqualTo("http://localhost/v1/testimonials?size=1&page=0");
        assertThat(response.getContent()).isNotEmpty();

    }

    @Test
    void updateTestimonial_shouldReturn200() throws Exception {

        TestimonialRequest update = TestimonialRequest.builder()
                .name("Name UPDATED")
                .content("Content UPDATED")
                .image("Image UPDATED")
                .build();

        mockMvc.perform(put(ApiConstants.TESTIMONIALS_URI + "/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.objectToJson(update)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void deleteTestimonial_shouldReturn204() throws Exception {
        mockMvc.perform(delete(ApiConstants.TESTIMONIALS_URI + "/1"))
                .andExpect(status().isNoContent())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }
}

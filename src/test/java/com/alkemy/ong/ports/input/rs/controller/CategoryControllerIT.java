package com.alkemy.ong.ports.input.rs.controller;

import com.alkemy.ong.H2Config;
import com.alkemy.ong.common.util.JsonUtils;
import com.alkemy.ong.ports.input.rs.api.ApiConstants;
import com.alkemy.ong.ports.input.rs.request.CreateCategoryRequest;
import com.alkemy.ong.ports.input.rs.response.CategoryResponse;
import com.alkemy.ong.ports.input.rs.response.CategoryResponseList;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringJUnitConfig(classes = H2Config.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CategoryControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Test
    @Order(1)
    @WithUserDetails("admin@somosmas.org")
    void createCategories_shouldReturn201() throws Exception {

        CreateCategoryRequest request = CreateCategoryRequest.builder()
                .name("clases apoyo")
                .description("clases para estudiantes sin recursos")
                .image("fnofenefo")
                .build();
        final String actualLocation = mockMvc.perform(post(ApiConstants.CATEGORIES_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.objectToJson(request)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getHeader(HttpHeaders.LOCATION);
        assertThat(actualLocation).isEqualTo("http://localhost/v1/categories/2");

    }

    @Test
    @Order(2)
    @WithUserDetails("admin@somosmas.org")
    void getCategory_shouldReturn200() throws Exception {

        String content = mockMvc.perform(get(ApiConstants.CATEGORIES_URI + "/2"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertThat(content).isNotBlank();

        CategoryResponse response = JsonUtils.jsonToObject(content, CategoryResponse.class);
        assertThat(response.getId()).isEqualTo(2);
        assertThat(response.getName()).isEqualTo("clases apoyo");
        assertThat(response.getDescription()).isEqualTo("clases para estudiantes sin recursos");
        assertThat(response.getImage()).isEqualTo("fnofenefo");

    }

    @Test
    @Order(3)
    @WithUserDetails("jdoe@somosmas.org")
    void getCategories_shouldReturn200() throws Exception {

        String content = mockMvc.perform(get(ApiConstants.CATEGORIES_URI))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertThat(content).isNotBlank();

        CategoryResponseList response = JsonUtils.jsonToObject(content, CategoryResponseList.class);
        assertThat(response.getTotalElements()).isEqualTo(2);
        assertThat(response.getTotalPages()).isEqualTo(1);
        assertThat(response.getNextUri()).isEqualTo("http://localhost/v1/categories?page=1");
        assertThat(response.getPreviousUri()).isEqualTo("http://localhost/v1/categories?page=0");
        assertThat(response.getContent()).isNotEmpty();

    }

    @Test
    @Order(4)
    @WithUserDetails("admin@somosmas.org")
    void updateCategory_shouldReturn204() throws Exception {

        CreateCategoryRequest request = CreateCategoryRequest.builder()
                .name("clases de apoyo updateadas")
                .build();

        mockMvc.perform(put(ApiConstants.CATEGORIES_URI + "/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.objectToJson(request)))
                .andExpect(status().isNoContent())
                .andDo(print());

    }

    @Test
    @Order(5)
    @WithUserDetails("admin@somosmas.org")
    void getUpdateCategory_shouldReturn200() throws Exception {

        String content = mockMvc.perform(get(ApiConstants.CATEGORIES_URI + "/2"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertThat(content).isNotBlank();

        CategoryResponse response = JsonUtils.jsonToObject(content, CategoryResponse.class);
        assertThat(response.getId()).isEqualTo(2);
        assertThat(response.getName()).isEqualTo("clases de apoyo updateadas");

    }

    @Test
    @Order(6)
    @WithUserDetails("admin@somosmas.org")
    void deleteCategory_shouldReturn204() throws Exception {

        mockMvc.perform(delete(ApiConstants.CATEGORIES_URI + "/2"))
                .andExpect(status().isNoContent())
                .andDo(print());

    }

    @Test
    @Order(7)
    @WithUserDetails("admin@somosmas.org")
    void createCategory_shouldReturn400() throws Exception {

        CreateCategoryRequest request = CreateCategoryRequest.builder()
                .description("clases para estudiantes sin recursos")
                .image("fnofenefo")
                .build();

        mockMvc.perform(post(ApiConstants.CATEGORIES_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.objectToJson(request)))
                .andExpect(status().isBadRequest())
                .andDo(print());

    }

    @Test
    @Order(8)
    @WithAnonymousUser
    void createCategory_shouldReturn401() throws Exception {

        CreateCategoryRequest request = CreateCategoryRequest.builder()
                .name("clases apoyo")
                .description("clases para estudiantes sin recursos")
                .image("fnofenefo")
                .build();

        mockMvc.perform(post(ApiConstants.CATEGORIES_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.objectToJson(request)))
                .andExpect(status().isUnauthorized())
                .andDo(print());

    }

    @Test
    @Order(9)
    @WithUserDetails("jdoe@somosmas.org")
    void createCategory_shouldReturn403() throws Exception {

        CreateCategoryRequest request = CreateCategoryRequest.builder()
                .name("clases apoyo")
                .description("clases para estudiantes sin recursos")
                .image("fnofenefo")
                .build();

        mockMvc.perform(post(ApiConstants.CATEGORIES_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.objectToJson(request)))
                .andExpect(status().isForbidden())
                .andDo(print());

    }

    @Test
    @Order(10)
    @WithAnonymousUser
    void getCategory_shouldReturn401() throws Exception {

        mockMvc.perform(get(ApiConstants.CATEGORIES_URI + "/2"))
                .andExpect(status().isUnauthorized())
                .andDo(print());

    }

    @Test
    @Order(11)
    @WithUserDetails("jdoe@somosmas.org")
    void getCategory_shouldReturn403() throws Exception {

        mockMvc.perform(get(ApiConstants.CATEGORIES_URI + "/2"))
                .andExpect(status().isForbidden())
                .andDo(print());

    }

    @Test
    @Order(12)
    @WithUserDetails("admin@somosmas.org")
    void getCategory_shouldReturn404() throws Exception {

        mockMvc.perform(get(ApiConstants.CATEGORIES_URI + "/2"))
                .andExpect(status().isNotFound())
                .andDo(print());

    }

    @Test
    @Order(13)
    @WithAnonymousUser
    void getCategories_shouldReturn401() throws Exception {

        mockMvc.perform(get(ApiConstants.CATEGORIES_URI))
                .andExpect(status().isUnauthorized())
                .andDo(print());

    }


    @Test
    @Order(14)
    @WithAnonymousUser
    void updateCategory_shouldReturn401() throws Exception {

        CreateCategoryRequest request = CreateCategoryRequest.builder()
                .name("clases apoyo")
                .build();

        mockMvc.perform(put(ApiConstants.CATEGORIES_URI + "2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.objectToJson(request)))
                .andExpect(status().isUnauthorized())
                .andDo(print());

    }

    @Test
    @Order(15)
    @WithUserDetails("jdoe@somosmas.org")
    void updateCategory_shouldReturn403() throws Exception {

        CreateCategoryRequest request = CreateCategoryRequest.builder()
                .name("clases apoyo")
                .build();

        mockMvc.perform(put(ApiConstants.CATEGORIES_URI + "2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.objectToJson(request)))
                .andExpect(status().isForbidden())
                .andDo(print());

    }

    @Test
    @Order(16)
    @WithUserDetails("admin@somosmas.org")
    void updateCategory_shouldReturn404() throws Exception {

        CreateCategoryRequest request = CreateCategoryRequest.builder()
                .name("clases apoyo")
                .build();

        mockMvc.perform(put(ApiConstants.CATEGORIES_URI + "99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.objectToJson(request)))
                .andExpect(status().isNotFound())
                .andDo(print());

    }

    @Test
    @Order(17)
    @WithAnonymousUser
    void deleteCategory_shouldReturn401() throws Exception {

        mockMvc.perform(delete(ApiConstants.CATEGORIES_URI + "/2"))
                .andExpect(status().isUnauthorized())
                .andDo(print());

    }

    @Test
    @Order(18)
    @WithUserDetails("jdoe@somosmas.org")
    void deleteCategory_shouldReturn4013() throws Exception {

        mockMvc.perform(delete(ApiConstants.CATEGORIES_URI + "/2"))
                .andExpect(status().isForbidden())
                .andDo(print());

    }

}
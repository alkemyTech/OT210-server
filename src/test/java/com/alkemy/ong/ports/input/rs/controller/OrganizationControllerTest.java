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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
        organization.setId(1L);
        organization.setName("Somos Mas");
        organization.setImage("https://cohorte-abril-98a56bb4.s3.amazonaws.com/1653997314766-LOGO-SOMOS_MAS.png");
        organization.setAddress("Barrio La Cava");
        organization.setPhone(1160112988);
        organization.setEmail("somosmasong4@gmail.com");
        organization.setWelcomeText("""
                Trabajar articuladamente con los distintos aspectos de la vida de las familias,
                generando espacios de desarrollo personal y familiar, brindando herramientas que logren mejorar la
                calidad de vida a través de su propio esfuerzo.""");
        organization.setAboutUsText("""
                Desde 1997 en Somos Más trabajamos con los chicos y chicas, mamás y papás, abuelos y vecinos
                del barrio La Cava generando procesos de crecimiento y de inserción social. Uniendo las manos de todas las familias,
                las que viven en el barrio y las que viven fuera de él, es que podemos pensar, crear y garantizar estos procesos.
                Somos una asociación civil sin fines de lucro que se creó en 1997 con la intención de dar alimento a las familias
                del barrio. Con el tiempo fuimos involucrándonos con la comunidad y agrandando y mejorando nuestra capacidad de
                trabajo. Hoy somos un centro comunitario que acompaña a más de 700 personas a través de las áreas de: Educación,
                deportes, primera infancia, salud, alimentación y trabajo social.""");
        organization.setFacebookContact("https://facebook.com/SomosMas");
        organization.setLinkedinContact("https://linkedIn.com/SomosMas");
        organization.setInstagramContact("https://instagram.com/SomosMas");

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
        assertThat(response.getId()).isEqualTo(1);
        assertThat(response.getName()).isEqualTo("Somos Mas");
        assertThat(response.getImage()).isEqualTo("https://cohorte-abril-98a56bb4.s3.amazonaws.com/1653997314766-LOGO-SOMOS_MAS.png");
        assertThat(response.getAddress()).isEqualTo("Barrio La Cava");
        assertThat(response.getPhone()).isEqualTo(1160112988);
        assertThat(response.getEmail()).isEqualTo("somosmasong4@gmail.com");
        assertThat(response.getFacebookContact()).isEqualTo("https://facebook.com/SomosMas");
        assertThat(response.getLinkedinContact()).isEqualTo("https://linkedIn.com/SomosMas");
        assertThat(response.getInstagramContact()).isEqualTo("https://instagram.com/SomosMas");
    }

    @Test
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

        mockMvc.perform(put(ApiConstants.ORGANIZATIONS_URI + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.objectToJson(request)))
                .andExpect(status().isNoContent())
                .andDo(print());
    }
}

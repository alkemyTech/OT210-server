package com.alkemy.ong.ports.input.rs.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrganizationRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String image;

    private String address;

    private Integer phone;

    @JsonProperty("welcome_text")
    private String welcomeText;

    @JsonProperty("about_us_text")
    private String aboutUsText;

    @JsonProperty("facebook_contact")
    private String facebookContact;

    @JsonProperty("linkedin_contact")
    private String linkedinContact;

    @JsonProperty("instagram_contact")
    private String instagramContact;

}

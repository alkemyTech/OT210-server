package com.alkemy.ong.ports.input.rs.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationResponse {

    private String name;

    private String image;

    private Integer phone;

    private String address;

    @JsonProperty("facebook_contact")
    private String facebookContact;

    @JsonProperty("linkedin_contact")
    private String linkedinContact;

    @JsonProperty("instagram_contact")
    private String instagramContact;

}

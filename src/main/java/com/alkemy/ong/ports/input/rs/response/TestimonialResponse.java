package com.alkemy.ong.ports.input.rs.response;

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
public class TestimonialResponse {

    @NotBlank
    @JsonProperty("name")
    private String name;

    @JsonProperty("image")
    private String image;

    @NotBlank
    @JsonProperty("content")
    private String content;

}

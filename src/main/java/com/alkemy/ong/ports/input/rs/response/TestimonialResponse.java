package com.alkemy.ong.ports.input.rs.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestimonialResponse {
    private String name;

    private String image;

    private String content;
}

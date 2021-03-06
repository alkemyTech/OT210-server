package com.alkemy.ong.ports.input.rs.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SlideResponse {

    private Long id;
    private String imageUrl;
    private String text;
    private Integer order;
    private OrganizationResponse organization;
}

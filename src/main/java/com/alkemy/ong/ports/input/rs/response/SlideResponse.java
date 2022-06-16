package com.alkemy.ong.ports.input.rs.response;

import com.alkemy.ong.domain.model.Organization;
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
    private Organization organization;
}

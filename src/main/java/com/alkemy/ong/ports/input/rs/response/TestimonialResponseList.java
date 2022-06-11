package com.alkemy.ong.ports.input.rs.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestimonialResponseList {

    private List<TestimonialResponse> content = null;
    private String nextUri;
    private String previousUri;
    private Integer totalPages;
    private Long totalElements;
}

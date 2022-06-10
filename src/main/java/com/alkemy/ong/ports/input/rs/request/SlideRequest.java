package com.alkemy.ong.ports.input.rs.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SlideRequest {

    private String img;
    private String text;
    private Integer order;
    private Long organizattionId;


}

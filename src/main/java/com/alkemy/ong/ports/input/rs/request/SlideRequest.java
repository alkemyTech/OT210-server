package com.alkemy.ong.ports.input.rs.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SlideRequest {

    @NotNull
    private String img;
    private String text;
    private Integer order;
    @NotNull
    private Long organizationId;


}

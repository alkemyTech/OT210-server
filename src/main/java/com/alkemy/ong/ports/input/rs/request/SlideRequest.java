package com.alkemy.ong.ports.input.rs.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SlideRequest {

    @NotBlank
    private String img;
    private String text;
    @Positive
    private Integer order;
    @NotNull
    @Positive
    private Long organizationId;

}

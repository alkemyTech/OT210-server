package com.alkemy.ong.ports.input.rs.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlkymerResponse {

    private Long id;

    private String name;

    @DateTimeFormat(
            iso = ISO.DATE_TIME
    )
    @JsonProperty("start_date")
    private Date startDate;

    @DateTimeFormat(
            iso = ISO.DATE_TIME
    )
    @JsonProperty("end_date")
    private Date endDate;

    private List<SkillResponse> skills = null;
}

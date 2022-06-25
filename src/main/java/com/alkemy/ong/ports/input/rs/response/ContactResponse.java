package com.alkemy.ong.ports.input.rs.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactResponse {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String message;
}

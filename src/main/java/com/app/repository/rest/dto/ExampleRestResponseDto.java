package com.app.repository.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExampleRestResponseDto {
    private String id;
    private String name;
    private Integer age;
}

package com.app.model.dto.example;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ExampleDto {
    private String id;
    private String name;
    private Integer age;
}

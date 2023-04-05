package com.app.model.dto.example.create;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateExampleDto {
    @NotEmpty
    private String name;
    @Min(1)
    private Integer age;
}

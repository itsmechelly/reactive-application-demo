package com.app.model.transformer;

import com.app.model.domain.Example;
import com.app.model.dto.example.ExampleDto;
import com.app.repository.rest.dto.ExampleRestResponseDto;

public class ExampleTransformer {

    public static ExampleDto exampleToExampleDto(final Example example) {
        return ExampleDto
                .builder()
                .id(example.getId())
                .name(example.getName())
                .age(example.getAge())
                .build();
    }

    public static ExampleDto exampleRestResponseDtoToExampleDto(final ExampleRestResponseDto exampleRestResponseDto) {
        return ExampleDto
                .builder()
                .id(exampleRestResponseDto.getId())
                .name(exampleRestResponseDto.getName())
                .age(exampleRestResponseDto.getAge())
                .build();
    }
}

package com.app.provider;

import com.app.model.dto.example.ExampleDto;
import com.app.model.dto.example.create.CreateExampleDto;
import com.app.model.enums.ExampleSourceEnum;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ExampleProvider {
    Flux<ExampleDto> getAllExample(Integer age, ExampleSourceEnum exampleSourceEnum);

    Mono<ExampleDto> getExampleById(String id);

    Mono<ExampleDto> createExample(CreateExampleDto createExampleDto);
}

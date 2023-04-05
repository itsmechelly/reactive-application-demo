package com.app.service;

import com.app.model.domain.Example;
import com.app.repository.rest.dto.ExampleRestResponseDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ExampleService {
    Flux<Example> getAllExample(Integer age);

    Flux<ExampleRestResponseDto> getExamplesFromExternalSource();

    Mono<Example> getExampleById(String id);

    Mono<Example> createExample(String name, Integer age);
}

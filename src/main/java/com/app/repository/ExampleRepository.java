package com.app.repository;

import com.app.model.domain.Example;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ExampleRepository {

    Flux<Example> getAllExample(Integer age);

    Mono<Example> getExampleById(String id);

    Mono<Example> createExample(Example example);
}

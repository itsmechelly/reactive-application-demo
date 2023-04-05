package com.app.service;

import com.app.model.domain.Example;
import com.app.repository.ExampleRepository;
import com.app.repository.rest.ExampleHttpRepository;
import com.app.repository.rest.dto.ExampleRestResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@AllArgsConstructor
public class ExampleServiceImpl implements ExampleService {

    private final ExampleRepository exampleRepository;
    private final ExampleHttpRepository exampleHttpRepository;

    @Override
    public Flux<Example> getAllExample(final Integer age) {
        log.info("getAllExample(), for age: {}", age);
        return exampleRepository.getAllExample(age)
                .doOnNext(example -> log.info("getAllExample()::ON_NEXT, age: {}. result - example: {}", age, example))
                .doOnError(throwable -> log.error("getAllExample()::ON_ERROR, throwable: {}", throwable.getMessage()));
    }

    @Override
    public Flux<ExampleRestResponseDto> getExamplesFromExternalSource() {
        log.info("getAllExamplesFromExternalSource()");
        return exampleHttpRepository.getExamples()
                .doOnNext(example -> log.info("getExamplesFromExternalSource()::ON_NEXT. result - example: {}", example))
                .doOnError(throwable -> log.error("getExamplesFromExternalSource()::ON_ERROR, throwable: {}", throwable.getMessage()));
    }

    @Override
    public Mono<Example> getExampleById(final String id) {
        log.info("getExampleById(), for id: {}", id);
        return exampleRepository.getExampleById(id)
                .doOnNext(example -> log.info("getExampleById()::ON_NEXT, id: {}. result - example: {}", id, example))
                .doOnError(throwable -> log.error("getExampleById()::ON_ERROR, throwable: {}", throwable.getMessage()));
    }

    @Override
    public Mono<Example> createExample(final String name, final Integer age) {
        log.info("getExampleById() for name: {}, age: {}", name, age);
        final Example example = Example.builder()
                .name(name)
                .age(age)
                .build();
        return exampleRepository.createExample(example)
                .doOnNext(exampleResult -> log.info("createExample()::ON_NEXT, name: {}, age: {} result - " +
                        "example: {}", name, age, exampleResult))
                .doOnError(throwable -> log.error("getExampleById()::ON_ERROR, throwable: {}", throwable.getMessage()));
    }
}

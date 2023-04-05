package com.app.provider;

import com.app.core.exception.NoContentException;
import com.app.model.dto.example.ExampleDto;
import com.app.model.dto.example.create.CreateExampleDto;
import com.app.model.enums.ExampleSourceEnum;
import com.app.model.transformer.ExampleTransformer;
import com.app.service.ExampleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@AllArgsConstructor
public class ExampleProviderImpl implements ExampleProvider {

    private final ExampleService exampleService;

    @Override
    public Flux<ExampleDto> getAllExample(final Integer age, final ExampleSourceEnum exampleSourceEnum) {
        log.info("getAllExample() REQUEST: age: {}, exampleSourceEnum: {}", age, exampleSourceEnum);
        if (exampleSourceEnum.equals(ExampleSourceEnum.INTERNAL)) {
            return exampleService.getAllExample(age)
                    .doOnNext(example -> log.info("getAllExample()::getAllExample::ON_NEXT age: {}, result: example: {}", age, example))
                    .doOnError(throwable -> log.info("getAllExample()::ON_ERROR throwable: {}", throwable.getMessage()))
                    .map(ExampleTransformer::exampleToExampleDto);
        }
        return exampleService.getExamplesFromExternalSource()
                .doOnNext(example -> log.info("getAllExample()::getExamplesFromExternalSource::" +
                        "ON_NEXT age: {}, result: example: {}", age, example))
                .doOnError(throwable -> log.info("getAllExample()::getExamplesFromExternalSource::" +
                        "ON_ERROR throwable: {}", throwable.getMessage()))
                .map(ExampleTransformer::exampleRestResponseDtoToExampleDto);
    }

    @Override
    public Mono<ExampleDto> getExampleById(final String id) {
        log.info("getExampleById() REQUEST: id: {}", id);
        return exampleService.getExampleById(id)
                .doOnNext(example -> log.info("getExampleById()::ON_NEXT id: {}, result: example: {}", id, example))
                .doOnError(throwable -> log.info("getExampleById()::ON_ERROR throwable: {}", throwable.getMessage()))
                .switchIfEmpty(Mono.error(NoContentException::new))
                .map(ExampleTransformer::exampleToExampleDto);
    }

    @Override
    public Mono<ExampleDto> createExample(final CreateExampleDto createExampleDto) {
        log.info("getExampleById() REQUEST: createExampleDto: {}", createExampleDto);
        final String name = createExampleDto.getName();
        final Integer age = createExampleDto.getAge();
        return exampleService.createExample(name, age)
                .doOnNext(example -> log.info("createExample()::ON_NEXT createExampleDto: {}, result: example: {}", createExampleDto, example))
                .doOnError(throwable -> log.info("createExample()::ON_ERROR throwable: {}", throwable.getMessage()))
                .map(ExampleTransformer::exampleToExampleDto);
    }
}

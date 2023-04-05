package com.app.controller;

import com.app.core.controller.BaseController;
import com.app.model.dto.example.ExampleDto;
import com.app.model.dto.example.create.CreateExampleDto;
import com.app.model.enums.ExampleSourceEnum;
import com.app.provider.ExampleProvider;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class ExampleControllerImpl extends BaseController implements ExampleController {

    private final ExampleProvider exampleProvider;

    @Override
    public Flux<ExampleDto> getAllExample(final Integer age, final ExampleSourceEnum exampleSourceEnum) {
        return exampleProvider.getAllExample(age, exampleSourceEnum);
    }

    @Override
    public Mono<ExampleDto> getExampleById(final String id) {
        return exampleProvider.getExampleById(id);
    }

    @Override
    public Mono<ExampleDto> createExample(final CreateExampleDto createExampleDto) {
        return exampleProvider.createExample(createExampleDto);
    }
}

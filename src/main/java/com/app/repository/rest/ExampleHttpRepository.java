package com.app.repository.rest;

import com.app.core.manager.http.HttpCommunicationManager;
import com.app.repository.rest.dto.ExampleRestResponseDto;
import reactor.core.publisher.Flux;

public interface ExampleHttpRepository extends HttpCommunicationManager {

    Flux<ExampleRestResponseDto> getExamples();
}

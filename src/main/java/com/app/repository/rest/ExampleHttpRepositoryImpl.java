package com.app.repository.rest;

import com.app.config.AppProperties;
import com.app.core.manager.http.HttpCommunicationManagerImpl;
import com.app.repository.rest.dto.ExampleRestResponseDto;
import com.app.repository.rest.request.GetExamplesNetworkRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Repository
@Slf4j
public class ExampleHttpRepositoryImpl extends HttpCommunicationManagerImpl implements ExampleHttpRepository {

    public ExampleHttpRepositoryImpl(final WebClient webClient, final AppProperties appProperties) {
        super(webClient, appProperties.getExampleBaseUrl());
    }

    public Flux<ExampleRestResponseDto> getExamples() {
        log.debug("getExamples()");
        final GetExamplesNetworkRequest getExamplesNetworkRequest = new GetExamplesNetworkRequest();
        return this.execList(getExamplesNetworkRequest)
                .doOnNext(exampleRestResponseDto -> log.info("getExamples() result: {}",
                        exampleRestResponseDto))
                .doOnError(throwable -> log.error("getExamples() . throwable: {}", throwable.getMessage()));
    }
}

package com.app.core.manager.http;

import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface HttpCommunicationManager {
    <T> Mono<T> exec(AbstractHttpClientRequest<T> httpClientRequest);

    <T> Flux<T> execList(AbstractHttpClientRequest<T> httpClientRequest);

    <T> Mono<ResponseEntity<T>> execWithResponseEntity(AbstractHttpClientRequest<T> httpClientRequest);
}

package com.app.core.manager.http;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class HttpCommunicationManagerImpl implements HttpCommunicationManager {
    private final WebClient httpClient;

    public HttpCommunicationManagerImpl(final WebClient webClient, final String baseUrl) {
        this.httpClient = webClient.mutate().baseUrl(baseUrl).build();
    }

    public <T> Mono<ResponseEntity<T>> execWithResponseEntity(final AbstractHttpClientRequest<T> httpClientRequest) {
        final String url = httpClientRequest.getPath();
        final Class<T> responseType = httpClientRequest.responseType();
        return makeRequest(httpClientRequest)
                .toEntity(responseType)
                .doOnNext(t -> log.debug("NEXT: " + t.toString()))
                .doOnCancel(() -> log.debug("CANCEL - url:" + url))
                .doOnDiscard(responseType, t -> log.debug("DISCARD - url:" + url))
                .doOnSuccess(t -> log.debug("SUCCESS - " + t.toString()))
                .doFinally(signalType -> log.debug("FINALLY - signal:" + signalType.toString()))
                .doOnTerminate(() -> log.debug("TERMINATE - url:" + url));
    }

    public <T> Mono<T> exec(final AbstractHttpClientRequest<T> httpClientRequest) {
        final String url = httpClientRequest.getPath();
        final Class<T> responseType = httpClientRequest.responseType();
        return makeRequest(httpClientRequest)
                .bodyToMono(responseType)
                .doOnNext(t -> log.debug("NEXT: " + t.toString()))
                .doOnCancel(() -> log.debug("CANCEL - url:" + url))
                .doOnDiscard(responseType, t -> log.debug("DISCARD - url:" + url))
                .doOnSuccess(t -> log.debug("SUCCESS - " + t.toString()))
                .doFinally(signalType -> log.debug("FINALLY - signal:" + signalType.toString()))
                .doOnTerminate(() -> log.debug("TERMINATE - url:" + url));
    }

    public <T> Flux<T> execList(final AbstractHttpClientRequest<T> httpClientRequest) {
        final String url = httpClientRequest.getPath();
        final Class<T> responseType = httpClientRequest.responseType();
        return makeRequest(httpClientRequest)
                .bodyToFlux(responseType)
                .doOnNext(t -> log.debug("NEXT: " + t.toString()))
                .doOnCancel(() -> log.debug("CANCEL - url:" + url))
                .doOnDiscard(responseType, t -> log.debug("DISCARD - url:" + url))
                .doFinally(signalType -> log.debug("FINALLY - signal:" + signalType.toString()))
                .doOnTerminate(() -> log.debug("TERMINATE - url:" + url));
    }

    private <T> WebClient.ResponseSpec makeRequest(final AbstractHttpClientRequest<T> httpClientRequest) {
        final String path = httpClientRequest.getPath();
        final Optional<MultiValueMap<String, String>> queryParams = Optional.ofNullable(httpClientRequest.getQueryParams());
        final Optional<HttpHeaders> headers = Optional.ofNullable(httpClientRequest.getHttpHeaders());
        Optional<HttpMethod> httpMethod = Optional.ofNullable(httpClientRequest.getHttpMethod());
        Optional<Object> body = Optional.ofNullable(httpClientRequest.getBody());

        return this.httpClient.method(httpMethod.orElse(HttpMethod.GET))
                .uri(uriBuilder -> uriBuilder.path(path)
                        .queryParams(queryParams.orElse(new LinkedMultiValueMap<>()))
                        .build())
                .contentType(httpClientRequest.getContentType())
                .bodyValue(body)
                .headers(baseHeaders ->
                        headers.ifPresent(baseHeaders::addAll)
                )
                .retrieve();
    }
}

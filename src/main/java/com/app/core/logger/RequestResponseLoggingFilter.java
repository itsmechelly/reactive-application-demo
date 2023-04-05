package com.app.core.logger;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class RequestResponseLoggingFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        final ServerHttpRequest httpRequest = exchange.getRequest();
        final String httpUrl = httpRequest.getURI().toString();
        final String method = httpRequest.getMethodValue();
        final HttpHeaders headers = httpRequest.getHeaders();
        final MultiValueMap<String, String> queryParams = httpRequest.getQueryParams();
        final MultiValueMap<String, HttpCookie> cookies = httpRequest.getCookies();

        RestLoggerUtil.logRequest(httpUrl, method, headers, queryParams, cookies);
        ServerHttpRequestDecorator loggingServerHttpRequestDecorator = new ServerHttpRequestDecorator(exchange.getRequest()) {
            String requestBody = "";

            @Override
            public Flux<DataBuffer> getBody() {
                return super.getBody()
                        .doOnNext(dataBuffer -> {
                            try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
                                Channels.newChannel(byteArrayOutputStream).write(dataBuffer.asByteBuffer().asReadOnlyBuffer());
                                requestBody = new String(byteArrayOutputStream.toByteArray(), StandardCharsets.UTF_8);
                                RestLoggerUtil.logRequestBody(httpUrl, method, requestBody);
                            } catch (IOException e) {
                                RestLoggerUtil.logRequestBodyError(httpUrl, method, requestBody, e);
                            }
                        });
            }
        };

        ServerHttpResponseDecorator loggingServerHttpResponseDecorator = new ServerHttpResponseDecorator(exchange.getResponse()) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                if (body instanceof Mono) {
                    body = ((Mono<? extends DataBuffer>) body)
                            .doOnNext(dataBuffer -> handleBufferBodyLog(dataBuffer, httpUrl, method));
                } else if (body instanceof Flux) {
                    body = ((Flux<? extends DataBuffer>) body)
                            .doOnNext(dataBuffer -> handleBufferBodyLog(dataBuffer, httpUrl, method));
                }
                return super.writeWith(body);
            }
        };
        return chain.filter(exchange.mutate().request(loggingServerHttpRequestDecorator).response(loggingServerHttpResponseDecorator).build());
    }

    private void handleBufferBodyLog(final DataBuffer dataBuffer, final String httpUrl, final String method) {
        String responseBody = "";
        try (final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            Channels.newChannel(byteArrayOutputStream).write(dataBuffer.asByteBuffer().asReadOnlyBuffer());
            responseBody = new String(byteArrayOutputStream.toByteArray(), StandardCharsets.UTF_8);
            RestLoggerUtil.logResponse(httpUrl, method, responseBody);
        } catch (Exception e) {
            RestLoggerUtil.logResponseError(httpUrl, method, responseBody, e);
        }

    }
}

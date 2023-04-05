package com.app.core.logger;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;

@Slf4j
public class RestLoggerUtil {

    public static void logRequest(final String url, final String method, final HttpHeaders headers,
                                  final MultiValueMap<String, String> queryParams, final MultiValueMap<String, HttpCookie> cookies) {
        log.info("URL: {}, Method: {}, Headers: {},  QueryParams: {}, Cookies: {}." +
                " MESSAGE: log incoming http request", url, method, headers, queryParams, cookies);
    }

    public static void logRequestBody(final String url, final String method, final String payload) {
        log.info("URL: {}, Method: {}, MESSAGE: log incoming http request, " +
            "Payload: {}", url, method, payload);
    }

    public static <T> void logRequestBodyError(final String url, final String method, final String payload, final T exception) {
        log.info("Log incoming request for URL: {}, Method: {}, MESSAGE: Fail to log incoming http request, " +
                "Error Type: {}, Payload: {}, Error: {}", url, method,
            exception.getClass(), payload, exception.toString());
    }

    public static void logResponse(final String url, final String method, final String payload) {
        log.info("Log outgoing response for URL: {}, Method: {}, MESSAGE: http request, " +
            "Payload: {}", url, method, payload);
    }

    public static <T> void logResponseError(final String url, final String method, final String payload, final T exception) {
        log.info("Log outgoing response for URL: {}, Method: {}, MESSAGE: fail to log http response, " +
            "Error Type: {}, Payload: {}, Error: {}", url, method, exception.getClass(), payload, exception.toString());
    }
}

package com.app.core.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class AppException extends BaseException {

    private HttpStatus httpStatus;
    private final String message;
    private final String details;

    public AppException(String message, String details) {
        this.message = message;
        this.details = details;
    }

    public AppException(String message, String details, HttpStatus httpStatus) {
        this.message = message;
        this.details = details;
        this.httpStatus = httpStatus;
    }
}

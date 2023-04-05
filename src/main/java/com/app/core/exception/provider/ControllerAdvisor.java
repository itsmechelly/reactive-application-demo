package com.app.core.exception.provider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.ResponseEntity.notFound;

@Slf4j
@RestControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(ResourceNotFoundException.class)
    ResponseEntity postNotFound(ResourceNotFoundException ex) {
        log.debug("handling exception::" + ex);
        return notFound().build();
    }
}

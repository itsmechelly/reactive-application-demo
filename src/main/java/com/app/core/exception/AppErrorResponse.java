package com.app.core.exception;

import com.app.core.exception.constant.AppErrorFieldConstant;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Builder
public class AppErrorResponse {
    @JsonProperty(AppErrorFieldConstant.FIELD_TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
    @JsonProperty(AppErrorFieldConstant.FIELD_MESSAGE)
    private String message;
    @JsonProperty(AppErrorFieldConstant.FIELD_DETAILS)
    private String details;
    @JsonProperty(AppErrorFieldConstant.FIELD_ERROR_CODE)
    private Integer errorCode;
}

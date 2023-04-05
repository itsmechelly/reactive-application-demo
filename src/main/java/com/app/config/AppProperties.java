package com.app.config;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@ConfigurationProperties(prefix = "app")
@Data
@Validated
public class AppProperties {
    @NotNull
    private String name;
    @NotNull
    private String version;
    @NotNull
    private String description;
    @NotNull
    private String baseUrl;
    @NotNull
    private String exampleBaseUrl;
}

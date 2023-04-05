package com.app.config.mongo;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "app.mongo")
@ConditionalOnProperty("app.mongo.uri")
@Data
@Validated
@Component
public class MongoProperties {
    @NotEmpty(message = "Mongo connection uri is required.")
    private String uri;
    @NotEmpty(message = "Mongo database is required.")
    private String database;
}

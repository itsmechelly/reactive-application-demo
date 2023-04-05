package com.app.config.openapi;

import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class OpenApiConfig {

    private final String[] packagedToMatch = {"com.app"};
    private final String API_V1_PREFIX = "/api/v1";

    @Bean
    public GroupedOpenApi openApiV1(@Qualifier("openApiV1Customiser") final OpenApiCustomiser openApiCustomiser) {
        final String[] paths = {"/api/v1/**"};
        final GroupedOpenApi openApi = GroupedOpenApi.builder()
                .group("v1")
                .pathsToMatch(paths)
                .packagesToScan(packagedToMatch)
                .addOpenApiCustomiser(openApiCustomiser).build();
        return openApi;
    }

    @Bean
    public OpenApiCustomiser openApiV1Customiser(@Value("${app.version}") final String appVersion) {
        return openApi -> {
            openApi.setServers(getServerForApi(API_V1_PREFIX));
            openApi.paths(removeSubstringVersionFromPath(openApi.getPaths(), API_V1_PREFIX));
            openApi.info(new Info()
                    .version(appVersion)
                    .title("Reactive Rest Service - V1")
                    .description("Api SPEC"));
        };
    }

    private List<Server> getServerForApi(final String ServerUrl) {
        final List<Server> serversList = new ArrayList<>();
        final Server server = new Server();
        server.setUrl(ServerUrl);
        serversList.add(server);
        return serversList;
    }

    private Paths removeSubstringVersionFromPath(final Paths openApiPaths, final String prefix) {
        final Paths paths = new Paths();
        for (Map.Entry<String, PathItem> entry : openApiPaths.entrySet()) {
            String newString = entry.getKey().replace("api/v1/", "");
            paths.put(newString, entry.getValue());
        }
        return paths;
    }
}

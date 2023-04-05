package com.app.core.manager.http;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;

@Getter
@Setter
public abstract class AbstractHttpClientRequest<T> {
    private MultiValueMap<String, Object> metadata;
    private HttpHeaders httpHeaders;
    private MultiValueMap<String, String> queryParams;
    private MediaType contentType;

    public abstract String getPath();

    public abstract HttpMethod getHttpMethod();

    public abstract Object getBody();

    public abstract Class<T> responseType();
}

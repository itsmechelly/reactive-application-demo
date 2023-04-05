package com.app.repository.rest.request;

import com.app.core.manager.http.AbstractHttpClientRequest;
import com.app.repository.rest.dto.ExampleRestResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;

@RequiredArgsConstructor
public class GetExamplesNetworkRequest extends AbstractHttpClientRequest<ExampleRestResponseDto> {

    @Override
    public String getPath() {
        return "/v3/c80117e1-98ea-4924-bb08-01edced621a7";
    }

    @Override
    public HttpMethod getHttpMethod() {
        return HttpMethod.GET;
    }

    @Override
    public Object getBody() {
        return null;
    }

    @Override
    public Class<ExampleRestResponseDto> responseType() {
        return ExampleRestResponseDto.class;
    }
}

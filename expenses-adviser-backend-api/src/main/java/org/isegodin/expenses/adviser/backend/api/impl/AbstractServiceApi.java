package org.isegodin.expenses.adviser.backend.api.impl;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author isegodin
 */
public abstract class AbstractServiceApi {

    protected final RestTemplate restTemplate = new RestTemplate();

    protected final String baseUrl;

    protected final String accessToken;

    public AbstractServiceApi(String baseUrl, String accessToken) {
        this.baseUrl = baseUrl;
        this.accessToken = accessToken;
    }

    protected UriComponentsBuilder builder() {
        return UriComponentsBuilder.fromUriString(baseUrl);
    }

    protected <T> HttpEntity<T> createHttpEntity(T body) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("access-token", accessToken);
        return new HttpEntity<>(body, headers);
    }
}

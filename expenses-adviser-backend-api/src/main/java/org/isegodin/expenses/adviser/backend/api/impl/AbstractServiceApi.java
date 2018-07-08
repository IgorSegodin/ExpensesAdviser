package org.isegodin.expenses.adviser.backend.api.impl;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author isegodin
 */
public abstract class AbstractServiceApi {

    protected final RestTemplate restTemplate = new RestTemplate();

    protected final String baseUrl;

    public AbstractServiceApi(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    protected UriComponentsBuilder builder() {
        return UriComponentsBuilder.fromUriString(baseUrl);
    }
}

package org.isegodin.expenses.adviser.backend.api.impl;

import org.isegodin.expenses.adviser.backend.api.UserServiceApi;
import org.isegodin.expenses.adviser.backend.api.dto.UserRequest;
import org.isegodin.expenses.adviser.backend.api.dto.UserResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * @author isegodin
 */
public class UserServiceApiImpl implements UserServiceApi {

    private final RestTemplate restTemplate = new RestTemplate();

    private final String baseUrl;

    public UserServiceApiImpl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public UserResponse createUser(UserRequest request) {
        URI uri = UriComponentsBuilder.fromUriString(baseUrl).path("/user").buildAndExpand().toUri();

        ResponseEntity<UserResponse> response = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(request), UserResponse.class);
        return response.getBody();
    }
}

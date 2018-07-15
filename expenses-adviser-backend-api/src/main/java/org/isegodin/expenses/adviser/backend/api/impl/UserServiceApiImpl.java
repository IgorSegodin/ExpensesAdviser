package org.isegodin.expenses.adviser.backend.api.impl;

import org.isegodin.expenses.adviser.backend.api.UserServiceApi;
import org.isegodin.expenses.adviser.backend.api.dto.UserRequest;
import org.isegodin.expenses.adviser.backend.api.dto.UserResponse;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.net.URI;

/**
 * @author isegodin
 */
public class UserServiceApiImpl extends AbstractServiceApi implements UserServiceApi {

    public UserServiceApiImpl(String baseUrl, String accessToken) {
        super(baseUrl, accessToken);
    }

    @Override
    public UserResponse createUser(UserRequest request) {
        URI uri = builder().path("/user").buildAndExpand().toUri();

        ResponseEntity<UserResponse> response = restTemplate.exchange(uri, HttpMethod.POST, createHttpEntity(request), UserResponse.class);
        return response.getBody();
    }
}

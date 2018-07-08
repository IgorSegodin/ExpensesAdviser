package org.isegodin.expenses.adviser.backend.api.impl;

import org.isegodin.expenses.adviser.backend.api.PaymentServiceApi;
import org.isegodin.expenses.adviser.backend.api.dto.PaymentRequest;
import org.isegodin.expenses.adviser.backend.api.dto.PaymentResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.List;
import java.util.UUID;

/**
 * @author isegodin
 */
public class PaymentServiceApiImpl extends AbstractServiceApi implements PaymentServiceApi {

    public PaymentServiceApiImpl(String baseUrl) {
        super(baseUrl);
    }

    @Override
    public PaymentResponse createPayment(PaymentRequest request) {
        URI uri = builder().path("/payment").buildAndExpand().toUri();

        ResponseEntity<PaymentResponse> response = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(request), PaymentResponse.class);
        return response.getBody();
    }

    @Override
    public List<PaymentResponse> listUserPayments(UUID userId) {
        URI uri = builder().path("/payment/filter").buildAndExpand().toUri();

        ResponseEntity<List<PaymentResponse>> response = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(userId), new ParameterizedTypeReference<List<PaymentResponse>>(){});
        return response.getBody();
    }
}

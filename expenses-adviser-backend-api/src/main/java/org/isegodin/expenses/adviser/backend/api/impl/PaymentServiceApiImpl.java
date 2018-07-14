package org.isegodin.expenses.adviser.backend.api.impl;

import org.isegodin.expenses.adviser.backend.api.PaymentServiceApi;
import org.isegodin.expenses.adviser.backend.api.dto.PageResponse;
import org.isegodin.expenses.adviser.backend.api.dto.PaymentFilterRequest;
import org.isegodin.expenses.adviser.backend.api.dto.PaymentRequest;
import org.isegodin.expenses.adviser.backend.api.dto.PaymentResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.net.URI;

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
    public PageResponse<PaymentResponse> listPayments(PaymentFilterRequest request) {
        URI uri = builder().path("/payment/filter").buildAndExpand().toUri();

        ResponseEntity<PageResponse<PaymentResponse>> response = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(request), new ParameterizedTypeReference<PageResponse<PaymentResponse>>(){});
        return response.getBody();
    }

    @Override
    public Long countPaymentValue(PaymentFilterRequest request) {
        URI uri = builder().path("/payment/count/value").buildAndExpand().toUri();

        ResponseEntity<Long> response = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(request), Long.class);
        return response.getBody();
    }
}

package org.isegodin.expenses.adviser.backend.api;

import org.isegodin.expenses.adviser.backend.api.dto.PaymentRequest;
import org.isegodin.expenses.adviser.backend.api.dto.PaymentResponse;

import java.util.List;
import java.util.UUID;

/**
 * @author isegodin
 */
public interface PaymentServiceApi {

    PaymentResponse createPayment(PaymentRequest request);

    List<PaymentResponse> listUserPayments(UUID userId);
}

package org.isegodin.expenses.adviser.backend.api;

import org.isegodin.expenses.adviser.backend.api.dto.PageResponse;
import org.isegodin.expenses.adviser.backend.api.dto.PaymentFilterRequest;
import org.isegodin.expenses.adviser.backend.api.dto.PaymentRequest;
import org.isegodin.expenses.adviser.backend.api.dto.PaymentResponse;

/**
 * @author isegodin
 */
public interface PaymentServiceApi {

    PaymentResponse createPayment(PaymentRequest request);

    PageResponse<PaymentResponse> listPayments(PaymentFilterRequest request);

    Long countPaymentValue(PaymentFilterRequest request);
}

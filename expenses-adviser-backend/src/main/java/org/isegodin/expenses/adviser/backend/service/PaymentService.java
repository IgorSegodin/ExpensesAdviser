package org.isegodin.expenses.adviser.backend.service;

import org.isegodin.expenses.adviser.backend.data.dto.PaymentDto;
import org.isegodin.expenses.adviser.backend.data.filter.PaymentFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * @author isegodin
 */
public interface PaymentService {

    PaymentDto save(PaymentDto dto);

    Page<PaymentDto> listPayments(PaymentFilter filter, PageRequest pageRequest);

    Long countPaymentValue(PaymentFilter filter);
}

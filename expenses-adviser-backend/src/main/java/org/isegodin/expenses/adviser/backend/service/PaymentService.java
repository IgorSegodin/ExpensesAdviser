package org.isegodin.expenses.adviser.backend.service;

import org.isegodin.expenses.adviser.backend.data.dto.PaymentDto;

import java.util.List;
import java.util.UUID;

/**
 * @author isegodin
 */
public interface PaymentService {

    PaymentDto save(PaymentDto dto);

    List<PaymentDto> listUserPayments(UUID userId);
}

package org.isegodin.expenses.adviser.backend.controller;

import org.isegodin.expenses.adviser.backend.api.PaymentServiceApi;
import org.isegodin.expenses.adviser.backend.api.dto.PaymentRequest;
import org.isegodin.expenses.adviser.backend.api.dto.PaymentResponse;
import org.isegodin.expenses.adviser.backend.data.dto.IdentifierDto;
import org.isegodin.expenses.adviser.backend.data.dto.PaymentDto;
import org.isegodin.expenses.adviser.backend.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author isegodin
 */
@RequestMapping("/payment")
@RestController
public class PaymentController implements PaymentServiceApi {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Override
    @PostMapping
    public PaymentResponse createPayment(@RequestBody PaymentRequest request) {
        PaymentDto dto = new PaymentDto();
        dto.setValue(request.getValue());
        dto.setIncome(request.getIncome());
        dto.setDescription(request.getDescription());
        if (request.getDate() != null) {
            dto.setDate(request.getDate().withOffsetSameInstant(ZoneOffset.UTC));
        }
        dto.setUser(new IdentifierDto<>(request.getUserId()));

        PaymentDto saved = paymentService.save(dto);

        PaymentResponse response = new PaymentResponse();
        response.setId(saved.getId());
        response.setValue(saved.getValue());
        response.setIncome(saved.getIncome());
        response.setDescription(saved.getDescription());
        response.setDate(saved.getDate());
        response.setUserId(saved.getUser().getId());
        return response;
    }

    @Override
    @PostMapping("/filter")
    public List<PaymentResponse> listUserPayments(@RequestBody UUID userId) {
        List<PaymentDto> list = paymentService.listUserPayments(userId);

        return list.stream().map(dto -> {
            PaymentResponse response = new PaymentResponse();
            response.setId(dto.getId());
            response.setValue(dto.getValue());
            response.setIncome(dto.getIncome());
            response.setDescription(dto.getDescription());
            response.setDate(dto.getDate());
            response.setUserId(dto.getUser().getId());
            return response;
        }).collect(Collectors.toList());
    }
}

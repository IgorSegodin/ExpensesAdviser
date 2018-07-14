package org.isegodin.expenses.adviser.backend.controller;

import org.isegodin.expenses.adviser.backend.api.PaymentServiceApi;
import org.isegodin.expenses.adviser.backend.api.dto.PageData;
import org.isegodin.expenses.adviser.backend.api.dto.PageResponse;
import org.isegodin.expenses.adviser.backend.api.dto.PaymentFilterRequest;
import org.isegodin.expenses.adviser.backend.api.dto.PaymentRequest;
import org.isegodin.expenses.adviser.backend.api.dto.PaymentResponse;
import org.isegodin.expenses.adviser.backend.data.dto.IdentifierDto;
import org.isegodin.expenses.adviser.backend.data.dto.PaymentDto;
import org.isegodin.expenses.adviser.backend.data.filter.PaymentFilter;
import org.isegodin.expenses.adviser.backend.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ValidationException;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
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
    public PageResponse<PaymentResponse> listPayments(@RequestBody PaymentFilterRequest request) {
        PageData pageData = Optional.ofNullable(request.getPageData()).orElse(new PageData(1, 20, "date"));
        PageRequest pageRequest = PageRequest.of(
                pageData.getPage(),
                pageData.getSize(),
                new Sort(pageData.isAsc() ? Sort.Direction.ASC : Sort.Direction.DESC,
                        pageData.getSortField()
                )
        );

        PaymentFilter filter = PaymentFilter.builder()
                .dateFrom(request.getDateFrom())
                .dateTo(request.getDateTo())
                .userId(request.getUserId())
                .build();

        Page<PaymentDto> page = paymentService.listPayments(filter, pageRequest);

        List<PaymentResponse> result = page.stream().map(dto -> {
            PaymentResponse response = new PaymentResponse();
            response.setId(dto.getId());
            response.setValue(dto.getValue());
            response.setIncome(dto.getIncome());
            response.setDescription(dto.getDescription());
            response.setDate(dto.getDate());
            response.setUserId(dto.getUser().getId());
            return response;
        }).collect(Collectors.toList());

        return new PageResponse<>(result, page.getTotalElements());
    }

    @Override
    @PostMapping("/count/value")
    public Long countPaymentValue(@RequestBody PaymentFilterRequest request) {
        if (request.getUserId() == null) {
            throw new ValidationException("Empty userId");
        }

        PaymentFilter filter = PaymentFilter.builder()
                .dateFrom(request.getDateFrom())
                .dateTo(request.getDateTo())
                .userId(request.getUserId())
                .build();

        return paymentService.countPaymentValue(filter);
    }
}

package org.isegodin.expenses.adviser.backend.service.impl;

import org.isegodin.expenses.adviser.backend.data.domain.Payment;
import org.isegodin.expenses.adviser.backend.data.dto.IdentifierDto;
import org.isegodin.expenses.adviser.backend.data.dto.PaymentDto;
import org.isegodin.expenses.adviser.backend.repository.PaymentRepository;
import org.isegodin.expenses.adviser.backend.repository.UserRepository;
import org.isegodin.expenses.adviser.backend.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author isegodin
 */
@Component
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    private final UserRepository userRepository;

    private final EntityManager entityManager;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository, UserRepository userRepository, EntityManager entityManager) {
        this.paymentRepository = paymentRepository;
        this.userRepository = userRepository;
        this.entityManager = entityManager;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PaymentDto save(PaymentDto dto) {
        Payment domain = new Payment();
        domain.setValue(dto.getValue());
        domain.setIncome(dto.getIncome());
        domain.setDescription(dto.getDescription());
        domain.setDate(dto.getDate());
        domain.setUser(userRepository.getOne(dto.getUser().getId()));

        Payment saved = paymentRepository.save(domain);

        PaymentDto result = new PaymentDto();
        result.setId(saved.getId());
        result.setValue(saved.getValue());
        result.setIncome(saved.getIncome());
        result.setDescription(saved.getDescription());
        result.setDate(saved.getDate());
        result.setUser(new IdentifierDto<>(saved.getUser().getId()));
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentDto> listUserPayments(UUID userId) {
        CriteriaBuilder b = entityManager.getCriteriaBuilder();
        CriteriaQuery<Payment> cq = b.createQuery(Payment.class);
        Root<Payment> root = cq.from(Payment.class);
        cq.select(root).where(b.equal(root.get("user").get("id"), userId));
        TypedQuery<Payment> tq = entityManager.createQuery(cq);
        List<Payment> resultList = tq.getResultList();

        return resultList.stream().map((domain -> {
            // TODO use normal projection
            PaymentDto dto = new PaymentDto();
            dto.setId(domain.getId());
            dto.setValue(domain.getValue());
            dto.setIncome(domain.getIncome());
            dto.setDescription(domain.getDescription());
            dto.setDate(domain.getDate());
            dto.setUser(new IdentifierDto<>(domain.getUser().getId()));
            return dto;
        })).collect(Collectors.toList());
    }
}

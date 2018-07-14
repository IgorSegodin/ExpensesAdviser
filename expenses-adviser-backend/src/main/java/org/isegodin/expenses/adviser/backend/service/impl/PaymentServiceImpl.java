package org.isegodin.expenses.adviser.backend.service.impl;

import org.hibernate.query.criteria.internal.OrderImpl;
import org.isegodin.expenses.adviser.backend.data.domain.Payment;
import org.isegodin.expenses.adviser.backend.data.dto.IdentifierDto;
import org.isegodin.expenses.adviser.backend.data.dto.PaymentDto;
import org.isegodin.expenses.adviser.backend.data.filter.PaymentFilter;
import org.isegodin.expenses.adviser.backend.repository.PaymentRepository;
import org.isegodin.expenses.adviser.backend.repository.UserRepository;
import org.isegodin.expenses.adviser.backend.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    public Page<PaymentDto> listPayments(PaymentFilter filter, PageRequest pageRequest) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Payment> query = builder.createQuery(Payment.class);
        Root<Payment> root = query.from(Payment.class);

        query.select(root).where(
                listFilterPredicates(filter, root, builder)
        );

        applyOrder(query, root, pageRequest);

        TypedQuery<Payment> typedQuery = entityManager.createQuery(query);
        typedQuery.setMaxResults(pageRequest.getPageSize());

        List<PaymentDto> result = typedQuery.getResultList().stream().map((domain -> {
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

        return new PageImpl<>(result);
    }

    @Override
    @Transactional(readOnly = true)
    public Long countPaymentValue(PaymentFilter filter) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Integer> query = builder.createQuery(Integer.class);
        Root<Payment> root = query.from(Payment.class);

        query.select(
                builder.sum(root.get("value"))
        ).where(
                listFilterPredicates(filter, root, builder)
        );

        TypedQuery<Integer> typedQuery = entityManager.createQuery(query);

        return Optional.ofNullable(typedQuery.getSingleResult()).orElse(0).longValue();
    }

    private Predicate[] listFilterPredicates(PaymentFilter filter, Root<?> root, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();

        if (filter.getUserId() != null) {
            predicates.add(builder.equal(root.get("user").get("id"), filter.getUserId()));
        }

        if (filter.getDateFrom() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("date"), filter.getDateFrom()));
        }
        if (filter.getDateTo() != null) {
            predicates.add(builder.lessThan(root.get("date"), filter.getDateTo()));
        }

        if (predicates.isEmpty()) {
            throw new IllegalArgumentException("Empty filter");
        }

        return predicates.stream().toArray(Predicate[]::new);
    }

    private void applyOrder(CriteriaQuery query, Root root, PageRequest pageRequest) {
        query.orderBy(
                pageRequest.getSort().stream()
                        .map(order -> new OrderImpl(root.get(order.getProperty()), order.isAscending()))
                        .collect(Collectors.toList())
        );
    }

}

package org.isegodin.expenses.adviser.telegram.service.impl;

import org.hibernate.query.criteria.internal.OrderImpl;
import org.isegodin.expenses.adviser.telegram.data.domain.UpdateEvent;
import org.isegodin.expenses.adviser.telegram.data.dto.UpdateEventDto;
import org.isegodin.expenses.adviser.telegram.data.filter.UpdateEventFilter;
import org.isegodin.expenses.adviser.telegram.repository.UpdateEventRepository;
import org.isegodin.expenses.adviser.telegram.service.UpdateEventService;
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
import java.util.stream.Collectors;

/**
 * @author i.segodin
 */
@Component
public class UpdateEventServiceImpl implements UpdateEventService {

    private final UpdateEventRepository updateEventRepository;

    private final EntityManager entityManager;

    @Autowired
    public UpdateEventServiceImpl(UpdateEventRepository updateEventRepository, EntityManager entityManager) {
        this.updateEventRepository = updateEventRepository;
        this.entityManager = entityManager;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UpdateEventDto save(UpdateEventDto dto) {
        UpdateEvent domain = dto.getId() != null ? updateEventRepository.findById(dto.getId()).orElse(new UpdateEvent()) : new UpdateEvent();
        domain.setId(dto.getId());
        domain.setRawUpdate(dto.getRawUpdate());
        domain.setStatus(dto.getStatus());
        domain.setErrorDescription(dto.getErrorDescription());
        domain.setEventDate(dto.getEventDate());
        domain.setTelegramUserId(dto.getTelegramUserId());
        updateEventRepository.save(domain);
        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public UpdateEventDto get(long id) {
        UpdateEvent domain = updateEventRepository.getOne(id);

        UpdateEventDto dto = new UpdateEventDto();
        dto.setId(domain.getId());
        dto.setRawUpdate(domain.getRawUpdate());
        dto.setStatus(domain.getStatus());
        dto.setErrorDescription(domain.getErrorDescription());
        dto.setEventDate(domain.getEventDate());
        dto.setTelegramUserId(domain.getTelegramUserId());
        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isExists(Long id) {
        return updateEventRepository.existsById(id);
    }

    @Override
    public Page<UpdateEventDto> listByFilter(UpdateEventFilter filter, PageRequest pageRequest) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<UpdateEvent> query = builder.createQuery(UpdateEvent.class);
        Root<UpdateEvent> root = query.from(UpdateEvent.class);

        // TODO projection
//        multiselect(
//                root.get("id").as(Long.class),
//                root.<String>get("rawUpdate").as(String.class),
//                root.get("status"),
//                root.get("errorDescription"),
//                root.get("eventDate"),
//                root.get("telegramUserId").as(Long.class)
//        )

        query.select(root).where(
                listFilterPredicates(filter, root, builder)
        );

        applyOrder(query, root, pageRequest);

        TypedQuery<UpdateEvent> typedQuery = entityManager.createQuery(query);
        typedQuery.setMaxResults(pageRequest.getPageSize());

        List<UpdateEventDto> result = typedQuery.getResultList().stream().map(domain -> {
            UpdateEventDto dto = new UpdateEventDto();
            dto.setId(domain.getId());
            dto.setRawUpdate(domain.getRawUpdate());
            dto.setStatus(domain.getStatus());
            dto.setErrorDescription(domain.getErrorDescription());
            dto.setEventDate(domain.getEventDate());
            dto.setTelegramUserId(domain.getTelegramUserId());
            return dto;
        }).collect(Collectors.toList());

        // TODO normal pagination
        return new PageImpl<>(result);
    }

    private Predicate[] listFilterPredicates(UpdateEventFilter filter, Root<UpdateEvent> root, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();

        if (filter.getStatuses() != null && !filter.getStatuses().isEmpty()) {
            predicates.add(root.get("status").in(filter.getStatuses()));
        }

        if (filter.getEventDateFrom() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("eventDate"), filter.getEventDateFrom()));
        }
        if (filter.getEventDateTo() != null) {
            predicates.add(builder.lessThan(root.get("eventDate"), filter.getEventDateTo()));
        }

        if (filter.getTelegramUserId() != null) {
            predicates.add(builder.equal(root.get("telegramUserId"), filter.getTelegramUserId()));
        }

        if (predicates.isEmpty()) {
            throw new IllegalArgumentException("Empty filter");
        }

        return predicates.stream().toArray(Predicate[]::new);
    }

    private void applyOrder(CriteriaQuery query, Root<UpdateEvent> root, PageRequest pageRequest) {
        query.orderBy(
                pageRequest.getSort().stream()
                        .map(order -> new OrderImpl(root.get(order.getProperty()), order.isAscending()))
                        .collect(Collectors.toList())
        );
    }
}

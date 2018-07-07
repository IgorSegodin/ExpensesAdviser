package org.isegodin.expenses.adviser.telegram.service.impl;

import org.isegodin.expenses.adviser.telegram.data.domain.UpdateEvent;
import org.isegodin.expenses.adviser.telegram.data.dto.UpdateEventDto;
import org.isegodin.expenses.adviser.telegram.repository.UpdateEventRepository;
import org.isegodin.expenses.adviser.telegram.service.UpdateEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author i.segodin
 */
@Component
public class UpdateEventServiceImpl implements UpdateEventService {

    private final UpdateEventRepository updateEventRepository;

    @Autowired
    public UpdateEventServiceImpl(UpdateEventRepository updateEventRepository) {
        this.updateEventRepository = updateEventRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UpdateEventDto save(UpdateEventDto dto) {
        UpdateEvent domain = new UpdateEvent();
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
}

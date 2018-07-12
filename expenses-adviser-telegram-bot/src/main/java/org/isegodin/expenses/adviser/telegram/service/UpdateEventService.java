package org.isegodin.expenses.adviser.telegram.service;

import org.isegodin.expenses.adviser.telegram.data.dto.UpdateEventDto;
import org.isegodin.expenses.adviser.telegram.data.filter.UpdateEventFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * @author i.segodin
 */
public interface UpdateEventService {

    UpdateEventDto save(UpdateEventDto dto);

    UpdateEventDto get(long id);

    boolean isExists(Long id);

    Page<UpdateEventDto> listByFilter(UpdateEventFilter filter, PageRequest pageRequest);
}

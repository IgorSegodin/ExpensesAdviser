package org.isegodin.expenses.adviser.telegram.service;

import org.isegodin.expenses.adviser.telegram.data.dto.UpdateEventDto;

/**
 * @author i.segodin
 */
public interface UpdateEventService {

    UpdateEventDto save(UpdateEventDto dto);

    UpdateEventDto get(long id);
}

package org.isegodin.expenses.adviser.telegram.data.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.isegodin.expenses.adviser.telegram.data.dict.UpdateEventStatus;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

/**
 * @author i.segodin
 */
@Data
@NoArgsConstructor
public class UpdateEventDto {

    Long id;

    String rawUpdate;

    UpdateEventStatus status = UpdateEventStatus.NEW;

    String errorDescription;

    OffsetDateTime eventDate = OffsetDateTime.now().withOffsetSameInstant(ZoneOffset.UTC);

    Long telegramUserId;

}

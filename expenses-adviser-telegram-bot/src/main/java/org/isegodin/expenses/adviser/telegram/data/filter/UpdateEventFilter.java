package org.isegodin.expenses.adviser.telegram.data.filter;

import lombok.Builder;
import lombok.Data;
import org.isegodin.expenses.adviser.telegram.data.dict.UpdateEventStatus;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Set;

/**
 * @author isegodin
 */
@Data
@Builder
public class UpdateEventFilter {

    Collection<UpdateEventStatus> statuses;

    OffsetDateTime eventDateFrom;

    OffsetDateTime eventDateTo;

    Long telegramUserId;
}

package org.isegodin.expenses.adviser.backend.data.filter;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * @author isegodin
 */
@Data
@Builder
public class PaymentFilter {

    UUID userId;

    OffsetDateTime dateFrom;

    OffsetDateTime dateTo;
}

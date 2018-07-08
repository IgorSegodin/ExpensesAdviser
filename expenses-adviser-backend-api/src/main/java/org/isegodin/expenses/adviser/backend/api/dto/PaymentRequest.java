package org.isegodin.expenses.adviser.backend.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

/**
 * @author isegodin
 */
@NoArgsConstructor
@Data
public class PaymentRequest {

    Integer value;

    Boolean income = false;

    String description;

    OffsetDateTime date = OffsetDateTime.now().withOffsetSameInstant(ZoneOffset.UTC);

    UUID userId;
}

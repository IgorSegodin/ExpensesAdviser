package org.isegodin.expenses.adviser.backend.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * @author isegodin
 */
@NoArgsConstructor
@Data
public class PaymentResponse {

    UUID id;

    Integer value;

    Boolean income = false;

    String description;

    OffsetDateTime date;

    UUID userId;
}

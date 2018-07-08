package org.isegodin.expenses.adviser.backend.data.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

/**
 * @author isegodin
 */
@Data
@NoArgsConstructor
public class PaymentDto {

    UUID id;

    Integer value;

    Boolean income = false;

    String description;

    OffsetDateTime date = OffsetDateTime.now().withOffsetSameInstant(ZoneOffset.UTC);

    IdentifierDto<UUID> user;
}

package org.isegodin.expenses.adviser.telegram.data.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author i.segodin
 */
@Data
@NoArgsConstructor
public class UpdateEventDto {

    Long id;

    String rawUpdate;
}

package org.isegodin.expenses.adviser.backend.data.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author isegodin
 */
@Data
@NoArgsConstructor
public class IdentifierDto<ID> {

    ID id;

    public IdentifierDto(ID id) {
        this.id = id;
    }
}

package org.isegodin.expenses.adviser.telegram.data.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * https://core.telegram.org/bots/api#update
 *
 * @author isegodin
 */
@NoArgsConstructor
@Data
public class UpdateDto {

    Long update_id;
    MessageDto message;
}

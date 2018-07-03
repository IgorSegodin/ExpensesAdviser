package org.isegodin.expenses.adviser.telegram.data.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * https://core.telegram.org/bots/api#user
 *
 * @author isegodin
 */
@NoArgsConstructor
@Data
public class UserDto {

    Long id;
    Boolean is_bot;
    String first_name;
    String last_name;
    String username;
    String language_code;

}

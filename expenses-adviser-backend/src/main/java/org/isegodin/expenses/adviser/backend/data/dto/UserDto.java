package org.isegodin.expenses.adviser.backend.data.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * @author isegodin
 */
@Data
@NoArgsConstructor
public class UserDto {

    UUID id;
    String firstName;
    String lastName;
}

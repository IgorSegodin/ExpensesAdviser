package org.isegodin.expenses.adviser.backend.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * @author isegodin
 */
@NoArgsConstructor
@Data
public class UserResponse {

    UUID id;
    String firstName;
    String lastName;
}

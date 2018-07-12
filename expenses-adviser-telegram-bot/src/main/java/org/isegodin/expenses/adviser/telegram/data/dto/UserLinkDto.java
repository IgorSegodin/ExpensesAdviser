package org.isegodin.expenses.adviser.telegram.data.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * @author isegodin
 */
@NoArgsConstructor
@Data
public class UserLinkDto {

    Long telegramUserId;

    UUID backendUserId;

    String firstName;

    String lastName;

    String username;

    String languageCode;
}

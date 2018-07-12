package org.isegodin.expenses.adviser.telegram.service;

import org.isegodin.expenses.adviser.telegram.data.dto.UserLinkDto;

import java.util.Optional;
import java.util.UUID;

/**
 * @author isegodin
 */
public interface UserLinkService {

    Optional<UUID> getBackendUserIdByTelegramUserId(Long telegramUserId);

    void create(UserLinkDto dto);
}

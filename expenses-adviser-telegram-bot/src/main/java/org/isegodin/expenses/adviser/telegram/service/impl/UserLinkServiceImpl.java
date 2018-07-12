package org.isegodin.expenses.adviser.telegram.service.impl;

import org.isegodin.expenses.adviser.telegram.data.domain.UserLink;
import org.isegodin.expenses.adviser.telegram.data.dto.UserLinkDto;
import org.isegodin.expenses.adviser.telegram.repository.UserLinkRepository;
import org.isegodin.expenses.adviser.telegram.service.UserLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

/**
 * @author isegodin
 */

@Component
public class UserLinkServiceImpl implements UserLinkService {

    private final UserLinkRepository userLinkRepository;

    @Autowired
    public UserLinkServiceImpl(UserLinkRepository userLinkRepository) {
        this.userLinkRepository = userLinkRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UUID> getBackendUserIdByTelegramUserId(Long telegramUserId) {
        UserLink link = userLinkRepository.findByTelegramUserId(telegramUserId);
        if (link != null) {
            return Optional.of(link.getBackendUserId());
        }
        return Optional.empty();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(UserLinkDto dto) {
        UserLink domain = new UserLink();
        domain.setTelegramUserId(dto.getTelegramUserId());
        domain.setBackendUserId(dto.getBackendUserId());
        domain.setFirstName(dto.getFirstName());
        domain.setLastName(dto.getLastName());
        domain.setUsername(dto.getUsername());
        domain.setLanguageCode(dto.getLanguageCode());

        userLinkRepository.save(domain);
    }
}

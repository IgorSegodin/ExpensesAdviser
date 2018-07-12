package org.isegodin.expenses.adviser.telegram.repository;

import org.isegodin.expenses.adviser.telegram.data.domain.UserLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author isegodin
 */
@Repository
public interface UserLinkRepository extends JpaRepository<UserLink, Long> {

    UserLink findByTelegramUserId(Long telegramUserId);
}

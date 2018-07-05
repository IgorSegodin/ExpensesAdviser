package org.isegodin.expenses.adviser.telegram.repository;

import org.isegodin.expenses.adviser.telegram.data.domain.UpdateEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author isegodin
 */
@Repository
public interface UpdateEventRepository extends JpaRepository<UpdateEvent, Long> {
}

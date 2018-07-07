package org.isegodin.expenses.adviser.backend.repository;

import org.isegodin.expenses.adviser.backend.data.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author isegodin
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
}

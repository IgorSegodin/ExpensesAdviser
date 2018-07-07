package org.isegodin.expenses.adviser.backend.api;

import org.isegodin.expenses.adviser.backend.api.dto.UserRequest;
import org.isegodin.expenses.adviser.backend.api.dto.UserResponse;

/**
 * @author isegodin
 */
public interface UserServiceApi {

    UserResponse createUser(UserRequest request);
}

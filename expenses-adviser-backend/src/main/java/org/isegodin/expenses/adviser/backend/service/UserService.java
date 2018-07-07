package org.isegodin.expenses.adviser.backend.service;

import org.isegodin.expenses.adviser.backend.data.dto.UserDto;

/**
 * @author isegodin
 */
public interface UserService {

    UserDto save(UserDto dto);
}

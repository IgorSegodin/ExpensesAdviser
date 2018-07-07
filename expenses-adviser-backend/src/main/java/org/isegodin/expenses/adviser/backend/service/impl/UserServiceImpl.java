package org.isegodin.expenses.adviser.backend.service.impl;

import org.isegodin.expenses.adviser.backend.data.domain.User;
import org.isegodin.expenses.adviser.backend.data.dto.UserDto;
import org.isegodin.expenses.adviser.backend.repository.UserRepository;
import org.isegodin.expenses.adviser.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author isegodin
 */
@Component
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserDto save(UserDto dto) {
        User domain = new User();
        domain.setFirstName(dto.getFirstName());
        domain.setLastName(dto.getLastName());

        User saved = userRepository.save(domain);

        UserDto result = new UserDto();
        result.setId(saved.getId());
        result.setFirstName(saved.getFirstName());
        result.setLastName(saved.getLastName());
        return result;
    }
}

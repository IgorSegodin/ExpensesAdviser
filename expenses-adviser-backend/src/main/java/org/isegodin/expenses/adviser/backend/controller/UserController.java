package org.isegodin.expenses.adviser.backend.controller;

import org.isegodin.expenses.adviser.backend.api.UserServiceApi;
import org.isegodin.expenses.adviser.backend.api.dto.UserRequest;
import org.isegodin.expenses.adviser.backend.api.dto.UserResponse;
import org.isegodin.expenses.adviser.backend.data.dto.UserDto;
import org.isegodin.expenses.adviser.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author isegodin
 */
@RequestMapping("/user")
@RestController
public class UserController implements UserServiceApi {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    @PostMapping
    public UserResponse createUser(@RequestBody UserRequest request) {
        UserDto dto = new UserDto();
        dto.setFirstName(request.getFirstName());
        dto.setLastName(request.getLastName());

        UserDto saved = userService.save(dto);

        UserResponse response = new UserResponse();
        response.setId(saved.getId());
        response.setFirstName(saved.getFirstName());
        response.setLastName(saved.getLastName());
        return response;
    }
}

package com.springbootapirestcourse.users.service;

import com.springbootapirestcourse.users.shared.dto.UserDto;

public interface UserService {
    public UserDto create(UserDto userDto);
}

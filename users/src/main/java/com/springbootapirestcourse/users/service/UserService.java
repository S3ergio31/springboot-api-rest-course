package com.springbootapirestcourse.users.service;

import com.springbootapirestcourse.users.shared.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    public UserDto create(UserDto userDto);
    public UserDto findByEmail(String email);
    public UserDto findByUserId(String id);
    public UserDto update(String id, UserDto userDto);
    public void delete(String userId);
}

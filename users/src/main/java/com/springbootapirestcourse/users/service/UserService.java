package com.springbootapirestcourse.users.service;

import com.springbootapirestcourse.users.shared.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    public UserDto create(UserDto userDto);
    public UserDto findByEmail(String email);
    public UserDto findByUserId(String id);
    public UserDto update(String id, UserDto userDto);
    public void delete(String userId);
    public List<UserDto> all(Integer page, Integer limit);
    public  Boolean verifyEmailToken(String token);
}

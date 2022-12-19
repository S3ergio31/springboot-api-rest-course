package com.springbootapirestcourse.users.service.implementation;

import com.springbootapirestcourse.users.io.entity.UserEntity;
import com.springbootapirestcourse.users.io.repository.UserRepository;
import com.springbootapirestcourse.users.service.UserService;
import com.springbootapirestcourse.users.shared.Utils;
import com.springbootapirestcourse.users.shared.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Utils utils;

    @Override
    public UserDto create(UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDto, userEntity);
        userEntity.setEncryptedPassword("test");
        userEntity.setUserId(utils.generateUserId(30));
        UserEntity storedUser = userRepository.save(userEntity);
        UserDto user = new UserDto();
        BeanUtils.copyProperties(storedUser, user);
        return user ;
    }
}

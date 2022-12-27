package com.springbootapirestcourse.users.service.implementation;

import com.springbootapirestcourse.users.io.entity.UserEntity;
import com.springbootapirestcourse.users.io.repository.UserRepository;
import com.springbootapirestcourse.users.service.UserService;
import com.springbootapirestcourse.users.shared.Utils;
import com.springbootapirestcourse.users.shared.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Utils utils;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDto create(UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDto, userEntity);
        userEntity.setEncryptedPassword(passwordEncoder.encode(userDto.getPassword()));
        userEntity.setUserId(utils.generateUserId(30));
        UserEntity storedUser = userRepository.save(userEntity);
        UserDto user = new UserDto();
        BeanUtils.copyProperties(storedUser, user);
        return user ;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = this.userRepository.findByEmail(email);

        if (userEntity == null ){
            throw new UsernameNotFoundException(email);
        }

        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
    }
}

package com.springbootapirestcourse.users.service.implementation;

import com.springbootapirestcourse.users.exception.UserServiceException;
import com.springbootapirestcourse.users.io.entity.UserEntity;
import com.springbootapirestcourse.users.io.repository.UserRepository;
import com.springbootapirestcourse.users.model.response.ErrorMessages;
import com.springbootapirestcourse.users.service.UserService;
import com.springbootapirestcourse.users.shared.Utils;
import com.springbootapirestcourse.users.shared.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        return user;
    }

    @Override
    public UserDto findByEmail(String email) {
        UserEntity userEntity = findUserEntityByEmail(email);
        return buildUserDto(userEntity);
    }

    @Override
    public UserDto findByUserId(String id) {
        UserEntity userEntity = this.userRepository.findByUserId(id);

        if (userEntity == null ){
            throw new UsernameNotFoundException(id);
        }

        return buildUserDto(userEntity);
    }

    @Override
    public UserDto update(String id, UserDto userDto) {
        UserDto userResponse = new UserDto();
        UserEntity userToUpdate = userRepository.findByUserId(id);

        if(userToUpdate == null) {
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }

        userToUpdate.setFirstName(userDto.getFirstName());
        userToUpdate.setLastName(userDto.getLastName());
        UserEntity updatedUser = userRepository.save(userToUpdate);
        BeanUtils.copyProperties(updatedUser, userResponse);

        return userResponse;
    }

    @Override
    public void delete(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        if(userEntity == null) {
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        userRepository.delete(userEntity);
    }

    @Override
    public List<UserDto> all(Integer page, Integer limit) {
        List<UserDto> userDtoList = new ArrayList<>();
        Page<UserEntity> usersPage = userRepository.findAll(PageRequest.of(page, limit));
        for (UserEntity user: usersPage.getContent()) {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(user, userDto);
            userDtoList.add(userDto);
        }
        return userDtoList;
    }

    private UserDto buildUserDto(UserEntity userEntity) {
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userEntity, userDto);
        return userDto;
    }

    private UserEntity findUserEntityByEmail(String email) {
        UserEntity userEntity = this.userRepository.findByEmail(email);

        if (userEntity == null ){
            throw new UsernameNotFoundException(email);
        }

        return userEntity;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = findUserEntityByEmail(email);
        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
    }
}

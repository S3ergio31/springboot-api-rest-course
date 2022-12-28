package com.springbootapirestcourse.users.controller;

import com.springbootapirestcourse.users.exception.UserServiceException;
import com.springbootapirestcourse.users.model.request.UserDatailsRequestModel;
import com.springbootapirestcourse.users.model.response.ErrorMessages;
import com.springbootapirestcourse.users.model.response.UserRest;
import com.springbootapirestcourse.users.service.UserService;
import com.springbootapirestcourse.users.shared.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.MimeType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
    value = "/users",
    produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
    consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
)
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public UserRest get(@PathVariable String id) {
        UserRest userRest = new UserRest();
        UserDto userDto = userService.findByUserId(id);
        BeanUtils.copyProperties(userDto, userRest);
        return userRest;
    }

    @PostMapping
    public UserRest create(@RequestBody UserDatailsRequestModel userDatails) throws Exception {
        if(userDatails.getFirstName().isEmpty()) {
            throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        }

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDatails, userDto);
        UserDto createdUser = this.userService.create(userDto);
        UserRest userRest = new UserRest();
        BeanUtils.copyProperties(createdUser, userRest);
        return userRest;
    }

    @PutMapping("/{id}")
    public UserRest update(@PathVariable String id, @RequestBody UserDatailsRequestModel userDatails) {
        UserRest userRest = new UserRest();
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDatails, userDto);

        UserDto updatedUser = userService.update(id, userDto);
        BeanUtils.copyProperties(updatedUser, userRest);
        return userRest;
    }

    @DeleteMapping
    public String delete() {
        return "Delete a user";
    }
}

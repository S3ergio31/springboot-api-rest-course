package com.springbootapirestcourse.users.controller;

import com.springbootapirestcourse.users.model.request.UserDatailsRequestModel;
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
@RequestMapping("/users")
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

    @PostMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public UserRest create(@RequestBody UserDatailsRequestModel userDatails) {
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDatails, userDto);
        UserDto createdUser = this.userService.create(userDto);
        UserRest userRest = new UserRest();
        BeanUtils.copyProperties(createdUser, userRest);
        return userRest;
    }

    @PutMapping
    public String update() {
        return "update a user";
    }

    @DeleteMapping
    public String delete() {
        return "Delete a user";
    }
}

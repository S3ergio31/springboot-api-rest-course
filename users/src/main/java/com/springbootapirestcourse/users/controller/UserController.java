package com.springbootapirestcourse.users.controller;

import com.springbootapirestcourse.users.exception.UserServiceException;
import com.springbootapirestcourse.users.model.request.UserDatailsRequestModel;
import com.springbootapirestcourse.users.model.response.ErrorMessages;
import com.springbootapirestcourse.users.model.response.OperationStatus;
import com.springbootapirestcourse.users.model.response.OperationStatusModel;
import com.springbootapirestcourse.users.model.response.UserRest;
import com.springbootapirestcourse.users.service.UserService;
import com.springbootapirestcourse.users.shared.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/{id}")
    public UserRest get(@PathVariable String id) {
        UserRest userRest = new UserRest();
        UserDto userDto = userService.findByUserId(id);
        BeanUtils.copyProperties(userDto, userRest);
        return userRest;
    }

    @GetMapping
    public List<UserRest> all(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "50") Integer limit
    ) {
        List<UserRest> userRestsList = new ArrayList<>();
        for (UserDto userDto: userService.all(page, limit)) {
            UserRest userRest = new UserRest();
            BeanUtils.copyProperties(userDto, userRest);
            userRestsList.add(userRest);
        }
        return userRestsList;
    }

    @PostMapping(
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public UserRest create(@RequestBody UserDatailsRequestModel userDatails) throws Exception {
        if(userDatails.getFirstName().isEmpty()) {
            throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        }

        UserDto userDto = this.modelMapper.map(userDatails, UserDto.class);
        UserDto createdUser = this.userService.create(userDto);
        return this.modelMapper.map(createdUser, UserRest.class);
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

    @DeleteMapping("/{id}")
    public OperationStatusModel delete(@PathVariable String id) {
        userService.delete(id);
        return new OperationStatusModel(
                OperationStatus.SUCCESSES.name(),
                RequestOperations.DELETE.name()
        );
    }
}

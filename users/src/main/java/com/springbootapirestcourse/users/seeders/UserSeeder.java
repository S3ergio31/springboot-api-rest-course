package com.springbootapirestcourse.users.seeders;

import com.springbootapirestcourse.users.service.UserService;
import com.springbootapirestcourse.users.shared.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class UserSeeder {
    @Autowired
    private UserService userService;
    @EventListener
    public void seed(ContextRefreshedEvent event) {
        UserDto userDto = new UserDto();
        userDto.setFirstName("Sergio");
        userDto.setLastName("Fidelis");
        userDto.setEmail("sergio@hotmail.com");
        userDto.setPassword("12345678");
        UserDto userCreated = userService.create(userDto);
        System.out.println("created user: ".concat(userCreated.getUserId()));
    }
}
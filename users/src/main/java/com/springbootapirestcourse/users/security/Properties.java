package com.springbootapirestcourse.users.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class Properties {

    @Autowired
    private Environment env;

    String getJwtSecret(){
        return env.getProperty("jwtSecret");
    }
}

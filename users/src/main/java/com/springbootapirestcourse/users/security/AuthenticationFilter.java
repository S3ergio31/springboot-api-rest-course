package com.springbootapirestcourse.users.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbootapirestcourse.users.ApplicationContext;
import com.springbootapirestcourse.users.model.request.UserLoginRequestModel;
import com.springbootapirestcourse.users.service.UserService;
import com.springbootapirestcourse.users.shared.dto.UserDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    private JwtHeaderManager jwtHeaderManager;

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.jwtHeaderManager = new JwtHeaderManager();
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws AuthenticationException
    {
        try {
            return authenticate(createUserLoginRequestModel(request));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private UserLoginRequestModel createUserLoginRequestModel(
            HttpServletRequest request
    ) throws IOException
    {
        return new ObjectMapper().readValue(
                request.getInputStream(),
                UserLoginRequestModel.class
        );
    }

    private Authentication authenticate(
            UserLoginRequestModel userLoginRequestModel
    )
    {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginRequestModel.getEmail(),
                        userLoginRequestModel.getPassword(),
                        new ArrayList<>()
                )
        );
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult
    ) throws IOException, ServletException
    {
        String username = ((User) authResult.getPrincipal()).getUsername();
        UserService userService = (UserService) ApplicationContext.getBean("userServiceImplementation");
        UserDto userDto = userService.findByEmail(username);
        jwtHeaderManager.
                writeJwtHeaderIntoResponse(username, response)
                .addHeader("UserID", userDto.getUserId());
    }
}

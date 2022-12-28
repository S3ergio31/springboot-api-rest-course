package com.springbootapirestcourse.users.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class AuthorizationFilter extends BasicAuthenticationFilter {

    private JwtHeaderManager jwtHeaderManager;

    public AuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        this.jwtHeaderManager = new JwtHeaderManager();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if(isException(request)) {
            chain.doFilter(request, response);
            return;
        }
        createUsernamePasswordAuthenticationToken(request);
        chain.doFilter(request, response);
    }

    private Boolean isException(HttpServletRequest request) {
        return
                request.getMethod().equals("POST") &&
                request.getRequestURI().equals(SecurityConstants.SIGN_UP_URL);
    }

    private void createUsernamePasswordAuthenticationToken(HttpServletRequest request) {
        String user = jwtHeaderManager.getTokenFromRequest(request);
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}

package com.springbootapirestcourse.users.security;


import com.springbootapirestcourse.users.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurity {
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationConfiguration authenticationConfiguration;

    public WebSecurity(UserService userService, BCryptPasswordEncoder passwordEncoder, AuthenticationConfiguration authenticationConfiguration) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationConfiguration = authenticationConfiguration;
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception{
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoder);

        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL)
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .addFilter(getAuthenticationFilter())
                .addFilter(getAuthorizationFilter());

        return http.build();
    }

    private AuthenticationFilter getAuthenticationFilter() throws Exception{
        AuthenticationFilter filter = new AuthenticationFilter(authenticationManager());
        filter.setFilterProcessesUrl(SecurityConstants.LOGIN_URL);
        return filter;
    }

    private AuthorizationFilter getAuthorizationFilter() throws Exception{
        return new AuthorizationFilter(authenticationManager());
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}

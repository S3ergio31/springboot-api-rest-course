package com.springbootapirestcourse.users.security;

import io.jsonwebtoken.JwtException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtHeaderManager {
    private Jwt jwt;

    public JwtHeaderManager(){
        this.jwt = new Jwt();
    }

    public HttpServletResponse writeJwtHeaderIntoResponse(
            String payload,
            HttpServletResponse response
    )
    {
        String token = jwt.encode(payload);
        response.addHeader(
                SecurityConstants.HEADER_STRING,
                SecurityConstants.TOKEN_PREFIX.concat(token)
        );
        return response;
    }

    public String getTokenFromRequest(HttpServletRequest request){
        String token = request.getHeader(SecurityConstants.HEADER_STRING);

        if(token == null || !token.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            throw new JwtException("Token not found");
        }

        token = token.replace(SecurityConstants.TOKEN_PREFIX, "");
        String payload = jwt.decode(token);
        if(payload != null) {
            return payload;
        }
        throw new JwtException("Token could not be decoded");
    }
}

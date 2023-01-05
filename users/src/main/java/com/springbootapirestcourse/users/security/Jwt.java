package com.springbootapirestcourse.users.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class Jwt {
    public String encode(String payload){
        return Jwts
                .builder()
                .setSubject(payload)
                .setExpiration(getExpiration())
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.getTokenSecret())
                .compact();
    }

    private Date getExpiration(){
        return new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME);
    }

    public Claims decode(String token) {
        return Jwts.parser()
                .setSigningKey(SecurityConstants.getTokenSecret())
                .parseClaimsJws(token)
                .getBody();
    }
}

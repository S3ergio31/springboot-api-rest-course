package com.springbootapirestcourse.users.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class Jwt {
    public String encode(String payload){
        return Jwts
                .builder()
                .setSubject(payload)
                .setExpiration(getExpiration())
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.TOKEN_SECRET)
                .compact();
    }

    private Date getExpiration(){
        return new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME);
    }

    public String decode(String token) {
        return Jwts.parser()
                .setSigningKey(SecurityConstants.TOKEN_SECRET)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}

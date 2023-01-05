package com.springbootapirestcourse.users.shared;

import com.springbootapirestcourse.users.security.Jwt;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;

@Service
public class Utils {

    private final Random RANDOM = new SecureRandom();
    private final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public String generateId(int length) {
        return generatedRandomString(length);
    }

    private String generatedRandomString(int length) {
        StringBuilder builder = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            builder.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }

        return new String (builder);
    }

    public static Boolean hasTokenExpired(String token) {
        Jwt jwt = new Jwt();
        Date expiration = jwt.decode(token).getExpiration();
        Date today = new Date();
        return expiration.before(today);
    }
}

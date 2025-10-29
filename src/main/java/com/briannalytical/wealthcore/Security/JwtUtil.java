package com.briannalytical.wealthcore.Security;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;


@Component
    public class JwtUtil {

        // secret key for jwt tokens * store in environment variables *
        private final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        // set token validity to 10 hours
        private static final long JWT_TOKEN_VALIDITY = 10 * 60 * 60 * 1000;

}

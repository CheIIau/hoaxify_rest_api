package com.hoaxify.hoaxify.common.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.access-token-expiration-minutes}")
    private int accessTokenExpirationMinutes;

    public String generateToken(String username) {
        var expirationDate = Date.from(ZonedDateTime.now().plusMinutes(accessTokenExpirationMinutes).toInstant());

        return JWT.create()
                .withSubject("User details")
                .withClaim("username", username)
                .withIssuedAt(new Date())
                .withIssuer(issuer)
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(secret));
    }

    public String verifyTokenAndRetrieveClaim(String token) throws JWTVerificationException {

        var verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("User details")
                .withIssuer(issuer)
                .build();

        var jwt = verifier.verify(token);
        return jwt.getClaim("username").asString();
    }

    public String generateRefreshToken() {
        return UUID.randomUUID().toString();
    }

}

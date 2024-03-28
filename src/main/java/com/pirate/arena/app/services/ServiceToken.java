package com.pirate.arena.app.services;

import com.pirate.arena.app.exceptions.TokenInvalidException;
import com.pirate.arena.app.request.RequestAmazon;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Optional;
import java.util.function.Function;


@Service
@RequiredArgsConstructor
@Slf4j
public class ServiceToken extends ServiceValidateRequest implements IServiceToken {

    private final Environment environment;
    private String SECRET_KEY = "413F4428472B4B6250655367566B5970337336763979244226452948404D6351";

    @Override
    public Key getSignInKey() {
        Optional<String> key = Optional.ofNullable(environment.getProperty("SECRET_KEY"));
        if (key.isEmpty())
            key = Optional.ofNullable(SECRET_KEY);
        byte[] keyByte = Decoders.BASE64.decode(key.get());
        return Keys.hmacShaKeyFor(keyByte);
    }

    @Override
    public Claims extractAllClaims(String token) throws Exception {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) throws Exception {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    @Override
    public Date extraExpiration(String token) throws Exception {
        return extractClaim(token, Claims::getExpiration);
    }

    @Override
    public boolean isTokenExpired(String token) throws Exception {
        return extraExpiration(token).before(new Date());
    }

    @Override
    public String isTokenValid(RequestAmazon requestAmazon) {
        validateInputs(Optional.ofNullable(requestAmazon));
        try {
            boolean isExpired = isTokenExpired(requestAmazon.authorizationToken());
            if (isExpired)
                throw new TokenInvalidException("[Token] Token Expired");
        } catch (Exception e) {
            throw new TokenInvalidException("[Token] Invalid token");
        }
        return """
                {
                  "principalId": "user",
                  "policyDocument": {
                    "Version": "2012-10-17",
                    "Statement": [
                      {
                        "Action": "execute-api:Invoke",
                        "Effect": "Allow",
                        "Resource": "$Token"
                      }
                    ]
                  }
                }
                """.replace("$Token", requestAmazon.methodArn());
    }

}

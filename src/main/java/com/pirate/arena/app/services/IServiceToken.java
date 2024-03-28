package com.pirate.arena.app.services;

import com.pirate.arena.app.exceptions.TokenInvalidException;
import com.pirate.arena.app.request.RequestAmazon;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.Optional;
import java.util.function.Function;

public interface IServiceToken {

    Key getSignInKey();

    Claims extractAllClaims(String token) throws Exception;

    <T> T extractClaim(String token, Function<Claims, T> claimsResolver) throws Exception;

    Date extraExpiration(String token) throws Exception;

    boolean isTokenExpired(String token) throws Exception;

    String isTokenValid(RequestAmazon requestAmazon);

}

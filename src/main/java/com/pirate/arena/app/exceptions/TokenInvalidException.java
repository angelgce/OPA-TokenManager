package com.pirate.arena.app.exceptions;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class TokenInvalidException extends RuntimeException{
    public TokenInvalidException(String message) {
        super(message);
        log.warn(message);
    }
}

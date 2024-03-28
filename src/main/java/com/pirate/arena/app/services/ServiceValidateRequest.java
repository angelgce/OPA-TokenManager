package com.pirate.arena.app.services;

import com.pirate.arena.app.exceptions.TokenInvalidException;

import java.util.Optional;

public abstract class ServiceValidateRequest {
    public void validateInputs(Optional<?> object) {
        if (object.toString().contains("null"))
            throw new TokenInvalidException("Error in the requests, some mandatory fields are missing "
                    .concat(object.toString()));
    }
}

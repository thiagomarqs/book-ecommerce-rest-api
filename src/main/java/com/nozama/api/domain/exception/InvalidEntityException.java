package com.nozama.api.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class InvalidEntityException extends RuntimeException {

    public InvalidEntityException(String message) {
        super(message);
    }

}

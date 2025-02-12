package com.production_planner.exception;

import lombok.Getter;

@Getter
public class NameAlreadyExistsException extends RuntimeException {

    private final int status;
    private final String message;

    public NameAlreadyExistsException(String field, String value) {
        this.status = 404;
        this.message = String.format("%s name already exists as: %s", field, value);
    }
}

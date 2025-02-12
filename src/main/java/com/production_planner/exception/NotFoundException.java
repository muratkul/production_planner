package com.production_planner.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {

    private final int status;
    private final String message;

    public NotFoundException(String field, Long value) {
        this.status = 404;
        this.message = String.format("%s not found with value: %d", field, value);
    }

    public NotFoundException(String field, String value) {
        this.status = 404;
        this.message = String.format("%s not found with value: %s", field, value);
    }

    public NotFoundException(String field) {
        this.status = 404;
        this.message = String.format("%s not found", field);
    }
}

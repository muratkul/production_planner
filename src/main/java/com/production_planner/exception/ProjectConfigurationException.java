package com.production_planner.exception;

import lombok.Getter;

@Getter
public class ProjectConfigurationException extends RuntimeException {

    private final int status;
    private final String message;

    public ProjectConfigurationException(String cause) {
        this.status = 500;
        this.message = String.format("Project Configuration Exception: %s", cause);
    }

}

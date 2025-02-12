package com.production_planner.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public final class ResponseDto {

    private int code;
    private String message;

}

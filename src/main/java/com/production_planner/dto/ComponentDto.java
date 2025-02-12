package com.production_planner.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class ComponentDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -7359928719958948252L;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;

    @NotEmpty
    private String name;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer quantity;

}

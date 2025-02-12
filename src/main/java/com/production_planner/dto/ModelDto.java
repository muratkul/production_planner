package com.production_planner.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class ModelDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 2574511164412813586L;

    private Long id;

    @Size(min = 1, max = 50, message = "name: a name, min 1 max 50 is required")
    private String name;

    @Min(value=0, message="percent: positive number, min 0 is required")
    @Max(value=100, message="percent: positive number, max 100 is required")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer percent;

    @NotEmpty(message = "components: components required")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Set<ComponentDto> components;

}

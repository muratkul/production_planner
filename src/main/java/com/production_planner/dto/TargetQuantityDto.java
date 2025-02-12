package com.production_planner.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.OrderBy;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class TargetQuantityDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -7359928719958948252L;

    @JsonIgnore
    private Long id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer quantity;

    @OrderBy(value = "id")
    private List<ComponentDto> components;

}

package com.production_planner.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class ProjectDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 2942916093018573879L;

    private Long id;

    @Size(min = 1, max = 50, message = "name: a name, min 1 max 50 is required")
    private String name;

    @NotEmpty(message = "models: models[] required")
    private List<PeriodDto> periods;

    @NotNull(message = "managementType: field required CONSTANT|MONTHLY|WEEKLY")
    private ManagementType managementType;

    private List<Integer> quantities;

}

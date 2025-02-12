package com.production_planner.controller;

import com.production_planner.dto.ProjectDto;
import com.production_planner.dto.ResponseDto;
import com.production_planner.dto.TargetQuantityDto;
import com.production_planner.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Project Controller",
        description = "This controller used to create update delete get or calculate the Projects"
)
@Validated
@RequestMapping("/api/project")
@RestController
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Operation(
            summary = "Get Project REST Service",
            description = "Returns the Project by given id"
    )
    @GetMapping("/get")
    public ResponseEntity<ProjectDto> get(@NotNull @RequestParam Long id) {
        ProjectDto projectDto = projectService.getProjectById(id);
        return ResponseEntity.ok(projectDto);
    }

    @Operation(
            summary = "Calculate Project REST Service",
            description = "Calculates the Project components by given id"
    )
    @GetMapping("/calculate")
    public ResponseEntity<List<TargetQuantityDto>> calculate(@NotNull @RequestParam Long id) {
        List<TargetQuantityDto> targetQuantityDtos = projectService.calculateComponents(id);
        return ResponseEntity.ok(targetQuantityDtos);
    }

    @Operation(
            summary = "Create Project REST Service",
            description = "Creates a Project by given ProjectDTO. The id is not needed for this operation"
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> create(@Valid @RequestBody ProjectDto projectDto) {

        projectService.create(projectDto);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setMessage("Successfully created project");
        responseDto.setCode(201);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);    }

    @Operation(
            summary = "Update Project REST Service",
            description = "Updates the Project by given ProjectDTO. The object must include the id as well"
    )
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> update(@Valid @RequestBody ProjectDto projectDto) {

        projectService.update(projectDto);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setMessage("Successfully updated project");
        responseDto.setCode(200);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(
            summary = "Delete Project REST Service",
            description = "Deletes the Project by given id"
    )
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> delete(@NotNull Long id) {

        projectService.delete(id);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setMessage("Successfully deleted project");
        responseDto.setCode(200);
        return ResponseEntity.ok(responseDto);
    }
}

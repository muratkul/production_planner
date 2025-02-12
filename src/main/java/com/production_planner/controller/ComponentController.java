package com.production_planner.controller;

import com.production_planner.dto.ComponentDto;
import com.production_planner.dto.ResponseDto;
import com.production_planner.service.ComponentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "Component Controller",
        description = "This controller used to create update delete or get the components used in Models"
)
@Validated
@RestController
@RequestMapping("/api/component")
public class ComponentController {

    private final ComponentService componentService;

    public ComponentController(ComponentService componentService) {
        this.componentService = componentService;
    }

    @Operation(
            summary = "Get Component REST Service",
            description = "Returns the Component by given id"
    )
    @GetMapping("/get")
    public ResponseEntity<ComponentDto> get(@NotNull @RequestParam Long id) {
        ComponentDto componentDto = componentService.getComponentById(id);
        return ResponseEntity.ok(componentDto);
    }

    @Operation(
            summary = "Create Component REST Service",
            description = "Creates a Component by given ComponentDTO. The id is not needed for this operation"
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> create(@Valid @RequestBody ComponentDto componentDto) {

        componentService.create(componentDto);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setMessage("Successfully created/updated component");
        responseDto.setCode(201);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @Operation(
            summary = "Update Component REST Service",
            description = "Updates the Component by given ComponentDTO. The object must include the id as well"
    )
    @PutMapping( "/update")
    public ResponseEntity<ResponseDto> update(@Valid @RequestBody ComponentDto componentDto) {

        componentService.update(componentDto);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setMessage("Successfully created/updated component");
        responseDto.setCode(200);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }


    @Operation(
            summary = "Delete Component REST Service",
            description = "Deletes the Component by given id"
    )
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> delete(@NotNull Long id) {

        componentService.delete(id);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setMessage("Successfully deleted component");
        responseDto.setCode(200);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}

package com.production_planner.controller;

import com.production_planner.dto.ModelDto;
import com.production_planner.dto.ResponseDto;
import com.production_planner.service.ModelService;
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
        name = "Model Controller",
        description = "This controller used to create update delete or get the models used in Projects"
)
@Validated
@RestController
@RequestMapping("/api/model")
public class ModelController {

    private final ModelService modelService;

    public ModelController(ModelService modelService) {
        this.modelService = modelService;
    }

    @Operation(
            summary = "Get Model REST Service",
            description = "Returns the Model by given id"
    )
    @GetMapping("/get")
    public ResponseEntity<ModelDto> get(@NotNull @RequestParam Long id) {
        ModelDto modelDto = modelService.getModelById(id);
        return ResponseEntity.ok(modelDto);
    }

    @Operation(
            summary = "Get All Models REST Service",
            description = "Returns all Models"
    )
    @GetMapping("/getAll")
    public ResponseEntity<List<ModelDto>> getAll() {
        List<ModelDto> modelDtoList = modelService.getAllModels();
        return ResponseEntity.ok(modelDtoList);
    }

    @Operation(
            summary = "Create Model REST Service",
            description = "Creates a Model by given ModelDTO. The id is not needed for this operation"
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> create(@Valid @RequestBody ModelDto modelDto) {

        modelService.create(modelDto);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setMessage("Successfully created model");
        responseDto.setCode(201);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @Operation(
            summary = "Update Model REST Service",
            description = "Updates the Model by given ModelDTO. The object must include the id as well"
    )
    @PutMapping( "/update")
    public ResponseEntity<ResponseDto> update(@Valid @RequestBody ModelDto modelDto) {

        modelService.update(modelDto);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setMessage("Successfully updated model");
        responseDto.setCode(200);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(
            summary = "Delete Model REST Service",
            description = "Deletes the Model by given id"
    )
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> delete(@NotNull Long id) {

        modelService.delete(id);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setMessage("Successfully deleted model");
        responseDto.setCode(200);
        return ResponseEntity.ok(responseDto);
    }
}

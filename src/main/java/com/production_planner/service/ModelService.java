package com.production_planner.service;

import com.production_planner.dto.ModelDto;

import java.util.List;


public interface ModelService {

    ModelDto getModelById(Long id);
    List<ModelDto> getAllModels();
    void create(ModelDto modelDto);
    void update(ModelDto modelDto);
    void delete(Long id);

}

package com.production_planner.service;

import com.production_planner.dto.ComponentDto;


public interface ComponentService {

    ComponentDto getComponentById(Long id);
    void create(ComponentDto componentDto);
    void update(ComponentDto componentDto);
    void delete(Long id);

}

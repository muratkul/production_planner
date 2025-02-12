package com.production_planner.mapper;

import com.production_planner.dto.ComponentDto;
import com.production_planner.model.Component;
import org.springframework.stereotype.Service;

@Service
public class ComponentMapper {

    private ComponentMapper() {
    }

    public static ComponentDto componentToDto(Component component) {
        ComponentDto dto = new ComponentDto();
        dto.setId(component.getId());
        dto.setName(component.getName());
        return dto;
    }

}

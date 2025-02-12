package com.production_planner.mapper;

import com.production_planner.dto.ComponentDto;
import com.production_planner.dto.ModelDto;
import com.production_planner.model.Component;
import com.production_planner.model.ComponentItem;
import com.production_planner.model.Model;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


@Service
public class ModelMapper {

    private ModelMapper() {

    }

    public static ModelDto modelToDto(Model model) {
        ModelDto dto = new ModelDto();
        dto.setId(model.getId());
        dto.setName(model.getName());
        return dto;
    }

    public static ComponentItem getComponentItem(ComponentDto c, Component component, Model model) {
        ComponentItem componentItem = new ComponentItem();
        componentItem.setComponent(component);
        componentItem.setModel(model);
        componentItem.setQuantity(c.getQuantity());
        return componentItem;
    }

    public static Set<ComponentDto> componentItemsToDto(Model model) {
        Set<ComponentDto> components = new HashSet<>();
        model.getComponents().forEach(c -> {
            ComponentDto componentDto = new ComponentDto();
            componentDto.setId(c.getComponent().getId());
            componentDto.setName(c.getComponent().getName());
            componentDto.setQuantity(c.getQuantity());
            components.add(componentDto);
        });
        return components;
    }
}

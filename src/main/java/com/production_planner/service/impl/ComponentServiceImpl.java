package com.production_planner.service.impl;

import com.production_planner.dto.ComponentDto;
import com.production_planner.exception.NameAlreadyExistsException;
import com.production_planner.exception.NotFoundException;
import com.production_planner.mapper.ComponentMapper;
import com.production_planner.model.Component;
import com.production_planner.model.ComponentItem;
import com.production_planner.model.Constants;
import com.production_planner.repository.ComponentItemRepository;
import com.production_planner.repository.ComponentRepository;
import com.production_planner.service.ComponentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ComponentServiceImpl implements ComponentService {

    private static final Logger LOG = LoggerFactory.getLogger(ComponentServiceImpl.class);
    private final ComponentRepository componentRepository;
    private final ComponentItemRepository componentItemRepository;

    public ComponentServiceImpl(ComponentRepository componentRepository, ComponentItemRepository componentItemRepository) {
        this.componentRepository = componentRepository;
        this.componentItemRepository = componentItemRepository;
    }

    public ComponentDto getComponentById(Long id) {
        Component component = componentRepository.findById(id).orElseThrow(() ->
                new NotFoundException(Constants.COMPONENT, id));

        ComponentDto componentDto = ComponentMapper.componentToDto(component);
        LOG.info("Get ComponentDto: {}", componentDto);
        return componentDto;
    }

    @Override
    public void create(ComponentDto componentDto) {
        if(componentRepository.findByName(componentDto.getName()).isPresent()) {
            throw new NameAlreadyExistsException(Constants.COMPONENT, componentDto.getName());
        }
        Component component = new Component();
        component.setName(componentDto.getName());
        componentRepository.save(component);
        LOG.info("Created ComponentDto: {}", componentDto);
    }

    @Override
    public void update(ComponentDto componentDto) {
        if (componentDto.getId() == null) {
            throw new NotFoundException(Constants.COMPONENT, "");
        }

        Component component = componentRepository.findById(componentDto.getId()).orElseThrow(() ->
                new NotFoundException(Constants.COMPONENT, componentDto.getId()));
        if (!component.getName().equals(componentDto.getName())
                && componentRepository.findByName(componentDto.getName()).isPresent()) {
            throw new NameAlreadyExistsException(Constants.COMPONENT, componentDto.getName());
        }

        component.setName(componentDto.getName());
        try {
            componentRepository.save(component);
        } catch (DataIntegrityViolationException e) {
            throw new NameAlreadyExistsException(Constants.COMPONENT, componentDto.getName());
        }

        LOG.info("Updated ComponentDto: {}", componentDto);
    }

    @Override
    public void delete(Long id) {
        Component component = componentRepository.findById(id).orElseThrow(() ->
                new NotFoundException(Constants.COMPONENT, id));

        Set<ComponentItem> componentItems = componentItemRepository.findAllByComponentId(id);
        componentItemRepository.deleteAll(componentItems);
        componentRepository.delete(component);
        LOG.info("Deleted Component with id: {}", id);
    }
}

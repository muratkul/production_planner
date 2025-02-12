package com.production_planner.service.impl;

import com.production_planner.dto.ModelDto;
import com.production_planner.exception.NameAlreadyExistsException;
import com.production_planner.exception.NotFoundException;
import com.production_planner.mapper.ModelMapper;
import com.production_planner.model.*;
import com.production_planner.repository.ComponentItemRepository;
import com.production_planner.repository.ComponentRepository;
import com.production_planner.repository.ModelItemRepository;
import com.production_planner.repository.ModelRepository;
import com.production_planner.service.ModelService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ModelServiceImpl implements ModelService {
    private final ModelItemRepository modelItemRepository;
    private final ComponentRepository componentRepository;

    private static final Logger LOG = LoggerFactory.getLogger(ModelServiceImpl.class);

    private final ModelRepository modelRepository;
    private final ComponentItemRepository componentItemRepository;

    public ModelServiceImpl(ModelRepository modelRepository, ComponentItemRepository componentItemRepository, ModelItemRepository modelItemRepository, ComponentRepository componentRepository) {
        this.modelRepository = modelRepository;
        this.componentItemRepository = componentItemRepository;
        this.modelItemRepository = modelItemRepository;
        this.componentRepository = componentRepository;
    }

    @Override
    public ModelDto getModelById(Long id) {
        final Model model = modelRepository.findById(id).orElseThrow(() ->
                new NotFoundException(Constants.MODEL, id));

        ModelDto modelDto = ModelMapper.modelToDto(model);
        modelDto.setComponents(ModelMapper.componentItemsToDto(model));

        LOG.info("Get ModelDto: {}", modelDto);
        return modelDto;
    }

    @Override
    public List<ModelDto> getAllModels() {
        final List<Model> models = modelRepository.findAll();

        if (CollectionUtils.isEmpty(models)) {
            throw new NotFoundException(Constants.MODEL);
        }
        List<ModelDto> modelDtoList = new ArrayList<>();
        models.forEach(model -> {
            ModelDto modelDto = ModelMapper.modelToDto(model);
            modelDto.setComponents(ModelMapper.componentItemsToDto(model));
            modelDtoList.add(modelDto);
        });

        LOG.info("Get All ModelDtoList: {}", modelDtoList);
        return modelDtoList;
    }

    @Override
    @Transactional
    public void create(ModelDto modelDto) {
        final Model model = new Model();
        saveModel(modelDto, model);
        LOG.info("Created ModelDto: {}", modelDto);
    }

    @Override
    @Transactional
    public void update(ModelDto modelDto) {
        final Model model = getModel(modelDto);
        saveModel(modelDto, model);
        LOG.info("Updated ModelDto: {}", modelDto);
    }

    private void saveModel(ModelDto modelDto, Model model) {
        if ((model.getName() == null || !model.getName().equals(modelDto.getName()))
                && modelRepository.findByName(modelDto.getName()).isPresent()) {
            throw new NameAlreadyExistsException(Constants.MODEL, modelDto.getName());
        }
        model.setName(modelDto.getName());
        modelRepository.save(model);
        Set<ComponentItem> componentItems = componentItemRepository.findAllByModelId(model.getId());
        if (!componentItems.isEmpty()) {
            componentItemRepository.deleteAll(componentItems);
        }

        modelDto.getComponents().forEach(c -> {
            Component component = componentRepository.findByName(c.getName())
                    .orElseThrow(() -> new NotFoundException(Constants.COMPONENT, c.getName()));
            ComponentItem componentItem = ModelMapper.getComponentItem(c, component, model);
            componentItemRepository.save(componentItem);
        });
    }

    private Model getModel(ModelDto modelDto) {
        if (modelDto.getId() == null) {
            throw new NotFoundException(Constants.MODEL, "");
        }
        return modelRepository.findById(modelDto.getId()).orElseThrow(() ->
                        new NotFoundException(Constants.MODEL, modelDto.getId()));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Model model = modelRepository.findById(id).orElseThrow(() ->
                new NotFoundException(Constants.MODEL, id));
        Set<ModelItem> modelItems = modelItemRepository.findAllByModelId(id);
        modelItemRepository.deleteAll(modelItems);
        modelRepository.delete(model);
        LOG.info("Deleted Model with id: {}", id);
    }
}

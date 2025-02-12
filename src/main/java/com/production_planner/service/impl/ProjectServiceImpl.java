package com.production_planner.service.impl;

import com.production_planner.dto.*;
import com.production_planner.exception.NameAlreadyExistsException;
import com.production_planner.exception.NotFoundException;
import com.production_planner.exception.ProjectConfigurationException;
import com.production_planner.mapper.ProjectMapper;
import com.production_planner.model.*;
import com.production_planner.repository.*;
import com.production_planner.service.ProjectService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ProjectServiceImpl implements ProjectService {

    private static final Logger LOG = LoggerFactory.getLogger(ProjectServiceImpl.class);
    private final ProjectRepository projectRepository;
    private final ModelItemRepository modelItemRepository;
    private final PeriodRepository periodRepository;
    private final TargetQuantityRepository targetQuantityRepository;
    private final ModelRepository modelRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository, ModelItemRepository modelItemRepository, PeriodRepository periodRepository, TargetQuantityRepository targetQuantityRepository, ModelRepository modelRepository) {
        this.projectRepository = projectRepository;
        this.modelItemRepository = modelItemRepository;
        this.periodRepository = periodRepository;
        this.targetQuantityRepository = targetQuantityRepository;
        this.modelRepository = modelRepository;
    }

    @Override
    public ProjectDto getProjectById(Long id) {
        final Project project = projectRepository.findById(id).orElseThrow(() ->
                new NotFoundException(Constants.PROJECT, id));

        ProjectDto projectDto = ProjectMapper.projectToDto(project);
        projectDto.setPeriods(ProjectMapper.periodsToPeriodDtoList(project.getPeriods()));
        projectDto.setQuantities(ProjectMapper.targetQuantitiesToIntegerSet(project.getQuantities()));

        LOG.info("Get ProjectDto: {}", projectDto);
        return projectDto;
    }

    @Override
    @Transactional
    public void create(ProjectDto projectDto) {
        final Project project = new Project();

        if (projectRepository.findByName(projectDto.getName()).isPresent()) {
            throw new NameAlreadyExistsException(Constants.PROJECT, projectDto.getName());
        }
        project.setName(projectDto.getName());
        project.setManagementType(projectDto.getManagementType());
        projectRepository.save(project);

        setProjectChildren(projectDto, project);
        LOG.info("Created Project with ProjectDto: {}", projectDto);
    }

    @Override
    @Transactional
    public void update(ProjectDto projectDto) {
        final Project project = getProject(projectDto);
        if (!project.getName().equals(projectDto.getName())
                && projectRepository.findByName(projectDto.getName()).isPresent()) {
            throw new NameAlreadyExistsException(Constants.PROJECT, projectDto.getName());
        }
        project.setName(projectDto.getName());
        project.setManagementType(projectDto.getManagementType());
        projectRepository.save(project);

        deleteProjectChildren(project);
        setProjectChildren(projectDto, project);

        LOG.info("Update Project with ProjectDto: {}", projectDto);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Project project = projectRepository.findById(id).orElseThrow(() ->
                new NotFoundException(Constants.PROJECT, id));
        projectRepository.delete(project);
        LOG.info("Deleted Project with id: {}", id);
    }

    private void setProjectChildren(ProjectDto projectDto, Project project) {
        if(CollectionUtils.isEmpty(projectDto.getPeriods()) || CollectionUtils.isEmpty(projectDto.getQuantities())) {
            throw new NotFoundException("No periods or quantities found");
        }
        int quantityCount = projectDto.getQuantities().size();
        int periodCount = projectDto.getPeriods().size();
        ManagementType managementType = projectDto.getManagementType();

        if (ManagementType.WEEKLY.equals(managementType) && quantityCount*4 != periodCount) {
            throw new ProjectConfigurationException("Wrong period count. It should be quantities * 4 size");
        } else if (ManagementType.MONTHLY.equals(managementType) && quantityCount != periodCount) {
            throw new ProjectConfigurationException("Wrong period count. It should be quantities size");
        } else if (ManagementType.CONSTANT.equals(managementType) && 1 != periodCount) {
            throw new ProjectConfigurationException("Wrong period count. It should be only 1");
        }

        projectDto.getPeriods().forEach(p -> {
            Period period = ProjectMapper.periodDtoToPeriod(project);
            periodRepository.save(period);
            p.getModels().forEach(m -> {
                Model model = modelRepository.findByName(m.getName())
                        .orElseThrow(() -> new NotFoundException(Constants.MODEL, m.getName()));
                ModelItem modelItem = ProjectMapper.getModelItem(m, model, period);
                modelItemRepository.save(modelItem);
            });
        });
        projectDto.getQuantities().forEach(q -> {
            TargetQuantity targetQuantity = new TargetQuantity();
            targetQuantity.setQuantity(q);
            targetQuantity.setProject(project);
            targetQuantityRepository.save(targetQuantity);
        });
    }

    private void deleteProjectChildren(Project project) {
        Set<Period> periods = periodRepository.findByProjectId(project.getId());
        Set<TargetQuantity> targetQuantities = targetQuantityRepository.findByProjectId(project.getId());
        if (!CollectionUtils.isEmpty(periods)) {
            periods.forEach(period -> {
                Set<ModelItem> modelItems = modelItemRepository.findAllByPeriodId(period.getId());
                if (!CollectionUtils.isEmpty(modelItems)) {
                    modelItemRepository.deleteAll(modelItems);
                }
            });
            periodRepository.deleteAll(periods);
        }
        if (!CollectionUtils.isEmpty(targetQuantities)) {
            targetQuantityRepository.deleteAll(targetQuantities);
        }
    }

    private Project getProject(ProjectDto projectDto) {
        if (projectDto.getId() == null) {
            throw new NotFoundException(Constants.PROJECT, "");
        }
        return projectRepository.findById(projectDto.getId()).orElseThrow(() ->
                        new NotFoundException(Constants.PROJECT, projectDto.getId()));
    }

    public List<TargetQuantityDto> calculateComponents(Long id) {
        List<TargetQuantityDto> targetQuantities = new ArrayList<>();
        Project project = projectRepository.findById(id).orElseThrow(() ->
                new NotFoundException(Constants.PROJECT, id));

        if (ManagementType.CONSTANT.equals(project.getManagementType())) {
            calculateConstant(project, targetQuantities);
        } else {
            targetQuantities = calculatePeriodic(project);
        }

        if (CollectionUtils.isEmpty(targetQuantities)) {
            throw new ProjectConfigurationException("Project Configuration Exception");
        }
        targetQuantities.sort(Comparator.comparing(TargetQuantityDto::getId));
        LOG.info("Calculated Project with Target Quantities: {}", targetQuantities);
        return targetQuantities;
    }

    private static void calculateConstant(Project project, List<TargetQuantityDto> targetQuantities) {
        project.getQuantities().forEach(quantity -> {
            TargetQuantityDto targetQuantityDto = new TargetQuantityDto();
            targetQuantityDto.setQuantity(quantity.getQuantity());
            targetQuantityDto.setId(quantity.getId());
            Map<String, ComponentDto> componentMap = new HashMap<>();
            if (project.getPeriods() != null) {
                project.getPeriods().forEach(period -> getModels(quantity.getQuantity(), period, componentMap));
            }
            targetQuantityDto.setComponents(new ArrayList<>(componentMap.values()));
            targetQuantities.add(targetQuantityDto);
        });
    }

    private static List<TargetQuantityDto> calculatePeriodic(Project project) {
        List<TargetQuantityDto> targetQuantities = new ArrayList<>();
        Map<Period, TargetQuantity> periodMap = new HashMap<>();
        int managementTypeMultiplier = ManagementType.WEEKLY.equals(project.getManagementType()) ? 4 : 1;
        for (int j = 0; j < project.getQuantities().size(); j++) {
            for (int i = 0; i < managementTypeMultiplier; i++) {
                int k = (j*managementTypeMultiplier)+i;
                periodMap.put(project.getPeriods().get(k), project.getQuantities().get(j));
            }
        }

        periodMap.values().stream().distinct().forEach(targetQuantity -> {
            List<Period> periods = getKeys(periodMap, targetQuantity);

            TargetQuantityDto targetQuantityDto = new TargetQuantityDto();
            targetQuantityDto.setId(targetQuantity.getId());
            targetQuantityDto.setQuantity(targetQuantity.getQuantity());
            Map<String,ComponentDto> componentMap = new HashMap<>();
            periods.forEach(period -> getModels(targetQuantity.getQuantity()/managementTypeMultiplier, period, componentMap));

            targetQuantityDto.setComponents(new ArrayList<>(componentMap.values()));
            targetQuantities.add(targetQuantityDto);
        });

        return targetQuantities;
    }
    public static  <K, V> List<K> getKeys(Map<K, V> map, V value) {
        List<K> keys = new ArrayList<>();
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                keys.add(entry.getKey());
            }
        }
        return keys;
    }
    private static void getModels(Integer quantity, Period period, Map<String, ComponentDto> componentMap) {
        if (period.getModels() != null) {
            AtomicInteger totalPercent = new AtomicInteger();
            period.getModels().forEach(m -> {
                if (m.getModel() != null && m.getModel().getComponents() != null) {
                    totalPercent.addAndGet(m.getPercent());
                    m.getModel().getComponents().forEach(component -> {
                        if (component != null && component.getComponent() != null) {
                            ComponentDto componentDto = new ComponentDto();
                            componentDto.setName(component.getComponent().getName());
                            componentDto.setQuantity(quantity * component.getQuantity() * m.getPercent() / 100);
                            mapComponent(componentMap, componentDto);
                        }
                    });
                }
            });
            if (totalPercent.get() != 100) {
                throw new ProjectConfigurationException("Total Model percentage is not 100");
            }
        }
    }

    private static void mapComponent(Map<String, ComponentDto> componentMap, ComponentDto componentDto) {
        ComponentDto c = componentMap.get(componentDto.getName());
        if (c != null) {
            c.setQuantity(c.getQuantity() + componentDto.getQuantity());
            componentMap.put(c.getName(), c);
        } else {
            componentMap.put(componentDto.getName(), componentDto);
        }
    }

}

package com.production_planner.service;

import com.production_planner.dto.ProjectDto;
import com.production_planner.dto.TargetQuantityDto;

import java.util.List;


public interface ProjectService {

    ProjectDto getProjectById(Long id);
    void create(ProjectDto projectDto);
    void update(ProjectDto projectDto);
    void delete(Long id);
    List<TargetQuantityDto> calculateComponents(Long id);

}

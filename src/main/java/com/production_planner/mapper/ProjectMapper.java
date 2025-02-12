package com.production_planner.mapper;

import com.production_planner.dto.ModelDto;
import com.production_planner.dto.PeriodDto;
import com.production_planner.dto.ProjectDto;
import com.production_planner.model.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectMapper {

    private ProjectMapper() {
    }

    public static ProjectDto projectToDto(Project project) {
        ProjectDto dto = new ProjectDto();
        dto.setId(project.getId());
        dto.setName(project.getName());
        dto.setManagementType(project.getManagementType());
        return dto;
    }

    public static ModelItem getModelItem(ModelDto m, Model model, Period period) {
        ModelItem modelItem = new ModelItem();
        modelItem.setPercent(m.getPercent());
        modelItem.setModel(model);
        modelItem.setPeriod(period);
        return modelItem;
    }

    public static Period periodDtoToPeriod(Project project) {
        Period period = new Period();
        period.setProject(project);
        return period;
    }

    public static List<PeriodDto> periodsToPeriodDtoList(List<Period> periods) {
        if (!CollectionUtils.isEmpty(periods)) {
            List<PeriodDto> periodDtos = new ArrayList<>();
            periods.forEach(period -> {
                PeriodDto periodDto = new PeriodDto();
                periodDto.setId(period.getId());
                if (!CollectionUtils.isEmpty(period.getModels())) {
                    List<ModelDto> modelDtos = new ArrayList<>();
                    period.getModels().forEach(modelItem -> {
                        ModelDto modelDto = ModelMapper.modelToDto(modelItem.getModel());
                        modelDto.setPercent(modelItem.getPercent());
                        modelDtos.add(modelDto);
                    });
                    periodDto.setModels(modelDtos);
                }
                periodDtos.add(periodDto);
            });
            return periodDtos;
        }
        return new ArrayList<>();
    }

    public static List<Integer> targetQuantitiesToIntegerSet(List<TargetQuantity> quantities) {
        if (!quantities.isEmpty()) {
            List<Integer> integers = new ArrayList<>();
            quantities.forEach(tq -> integers.add(tq.getQuantity()));
            return integers;
        }
        return new ArrayList<>();
    }

}

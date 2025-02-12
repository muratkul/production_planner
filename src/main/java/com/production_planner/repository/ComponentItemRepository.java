package com.production_planner.repository;

import com.production_planner.model.ComponentItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ComponentItemRepository extends JpaRepository<ComponentItem, Long> {

    Set<ComponentItem> findAllByModelId(Long modelId);

    Set<ComponentItem> findAllByComponentId(Long componentId);
}

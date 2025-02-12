package com.production_planner.repository;

import com.production_planner.model.ModelItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ModelItemRepository extends JpaRepository<ModelItem, Long> {

    Set<ModelItem> findAllByPeriodId(Long id);

    Set<ModelItem> findAllByModelId(Long id);
}

package com.production_planner.repository;

import com.production_planner.model.TargetQuantity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface TargetQuantityRepository extends JpaRepository<TargetQuantity, Long> {

    Set<TargetQuantity> findByProjectId(Long id);
}

package com.production_planner.repository;

import com.production_planner.model.Period;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PeriodRepository extends JpaRepository<Period, Long> {

    Set<Period> findByProjectId(Long id);
}

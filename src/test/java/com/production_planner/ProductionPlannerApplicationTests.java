package com.production_planner;

import com.production_planner.mapper.ProjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductionPlannerApplicationTests {

    @Test
    void contextLoads() {

        Assertions.assertNotNull(ProjectMapper.periodsToPeriodDtoList(null));

    }

}

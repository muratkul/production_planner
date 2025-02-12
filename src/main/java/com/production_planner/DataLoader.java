package com.production_planner;

import com.production_planner.model.Component;
import com.production_planner.model.ComponentItem;
import com.production_planner.model.Model;
import com.production_planner.repository.ComponentItemRepository;
import com.production_planner.repository.ComponentRepository;
import com.production_planner.repository.ModelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;


@Service
public class DataLoader implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);
    private final ComponentRepository componentRepository;
    private final ModelRepository modelRepository;
    private final ComponentItemRepository componentItemRepository;

    @Value("${planner.api.create.test.data}")
    private boolean createTestData;

    public DataLoader(ComponentRepository componentRepository, ModelRepository modelRepository, ComponentItemRepository componentItemRepository) {

        this.componentRepository = componentRepository;
        this.modelRepository = modelRepository;
        this.componentItemRepository = componentItemRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (componentRepository.count() == 0 && createTestData) {
            Component component = new Component();
            component.setName("Comp_Battery_E237");
            componentRepository.save(component);
            Component component2 = new Component();
            component2.setName("Comp_Display_5_IPS");
            componentRepository.save(component2);
            Component component3 = new Component();
            component3.setName("Comp_Speaker_Base");
            componentRepository.save(component3);
            Component component4 = new Component();
            component4.setName("Comp_Battery_E37");
            componentRepository.save(component4);
            Component component5 = new Component();
            component5.setName("Comp_Display_15_IPS");
            componentRepository.save(component5);

            Model model = new Model();
            model.setName("A20");
            modelRepository.save(model);

            ComponentItem componentItem = new ComponentItem();
            componentItem.setComponent(component4);
            componentItem.setQuantity(1);
            componentItem.setModel(model);
            componentItemRepository.save(componentItem);

            ComponentItem componentItem2 = new ComponentItem();
            componentItem2.setComponent(component2);
            componentItem2.setQuantity(1);
            componentItem2.setModel(model);
            componentItemRepository.save(componentItem2);

            ComponentItem componentItem3 = new ComponentItem();
            componentItem3.setComponent(component3);
            componentItem3.setQuantity(2);
            componentItem3.setModel(model);
            componentItemRepository.save(componentItem3);

            Model model2 = new Model();
            model2.setName("L15");
            modelRepository.save(model2);

            ComponentItem componentItem4 = new ComponentItem();
            componentItem4.setComponent(component);
            componentItem4.setQuantity(1);
            componentItem4.setModel(model2);
            componentItemRepository.save(componentItem4);

            ComponentItem componentItem5 = new ComponentItem();
            componentItem5.setComponent(component5);
            componentItem5.setQuantity(1);
            componentItem5.setModel(model2);
            componentItemRepository.save(componentItem5);

            ComponentItem componentItem6 = new ComponentItem();
            componentItem6.setComponent(component3);
            componentItem6.setQuantity(4);
            componentItem6.setModel(model2);
            componentItemRepository.save(componentItem6);

            logger.info("Components and Models created successfully");

        }
    }
}

package ru.rosatom.oilspill.repository;

import org.springframework.data.repository.CrudRepository;
import ru.rosatom.oilspill.model.entity.Plant;

import java.util.List;

public interface PlantRepository extends CrudRepository<Plant, Long> {
    List<Plant> findAll();
}

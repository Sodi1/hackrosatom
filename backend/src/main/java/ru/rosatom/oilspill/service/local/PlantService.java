package ru.rosatom.oilspill.service.local;

import ru.rosatom.oilspill.model.dto.PlantDto;
import ru.rosatom.oilspill.model.entity.Plant;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface PlantService {

    List<Plant> findAll();

    Optional<Plant> findById(@NotNull Long id);

    Plant save(PlantDto plantDto);
}

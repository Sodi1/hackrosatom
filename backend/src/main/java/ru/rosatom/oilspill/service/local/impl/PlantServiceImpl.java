package ru.rosatom.oilspill.service.local.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.rosatom.oilspill.mapper.PlantMapper;
import ru.rosatom.oilspill.model.dto.PlantDto;
import ru.rosatom.oilspill.model.entity.Plant;
import ru.rosatom.oilspill.repository.PlantRepository;
import ru.rosatom.oilspill.service.local.PlantService;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class PlantServiceImpl implements PlantService {

    private final PlantRepository repository;
    private final PlantMapper plantMapper;

    @Cacheable("plantFindAll")
    public List<Plant> findAll() {
        log.trace(">>findAll...");
        return repository.findAll();
    }

    public Optional<Plant> findById(@NotNull Long id) {
        log.trace(">>findById... id={}", id);
        return repository.findById(id);
    }

    @Override
    public Plant save(PlantDto plantDto) {
        return repository.save(plantMapper.toEntity(plantDto));
    }
}

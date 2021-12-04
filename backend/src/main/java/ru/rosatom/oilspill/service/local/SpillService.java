package ru.rosatom.oilspill.service.local;

import ru.rosatom.oilspill.model.dto.SpillDto;
import ru.rosatom.oilspill.model.entity.Spill;

import java.util.List;
import java.util.Optional;

public interface SpillService {

    List<Spill> findAll();

    Optional<SpillDto> findById(Long id);

    Spill save(SpillDto spillDto);
}

package ru.rosatom.oilspill.service.local.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rosatom.oilspill.mapper.SpillMapper;
import ru.rosatom.oilspill.model.dto.SpillDto;
import ru.rosatom.oilspill.model.entity.Spill;
import ru.rosatom.oilspill.repository.SpillRepository;
import ru.rosatom.oilspill.service.local.SpillService;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class SpillServiceImpl implements SpillService {

    private final SpillRepository repository;
    private final SpillMapper mapper;


    @Override
    public List<Spill> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<SpillDto> findById(Long id) {
        var spill = repository.findById(id);
        return spill.map(mapper::toDto);
    }

    @Override
    public Spill save(SpillDto spillDto) {
        return repository.save(mapper.toEntity(spillDto));
    }
}

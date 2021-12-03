package ru.rosatom.oilspill.service.local.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rosatom.oilspill.mapper.DeviceMapper;
import ru.rosatom.oilspill.model.dto.DeviceDto;
import ru.rosatom.oilspill.model.entity.Device;
import ru.rosatom.oilspill.repository.DeviceRepository;
import ru.rosatom.oilspill.service.local.DeviceService;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository repository;
    private final DeviceMapper mapper;

    @Override
    public Optional<Device> findById(@NotNull Long id){
        return repository.findById(id);
    }

    @Override
    public List<Device> findAll() {
        return repository.findAll();
    }

    @Override
    public Device save(@NotNull DeviceDto device) {
        return repository.save(mapper.toEntity(device));
    }

    @Override
    public List<Device> findByPlantId(@NotNull Long plantId) {
        return repository.findByPlantId(plantId);
    }
}

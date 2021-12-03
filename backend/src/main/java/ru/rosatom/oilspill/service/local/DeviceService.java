package ru.rosatom.oilspill.service.local;

import ru.rosatom.oilspill.model.dto.DeviceDto;
import ru.rosatom.oilspill.model.entity.Device;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface DeviceService {

    Optional<Device> findById(@NotNull Long id);

    List<Device> findAll();

    Device save(@NotNull DeviceDto device);

    List<Device> findByPlantId(@NotNull Long plantId);

}

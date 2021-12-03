package ru.rosatom.oilspill.service.local;

import ru.rosatom.oilspill.model.dto.TelemetryDto;
import ru.rosatom.oilspill.model.entity.Telemetry;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface TelemetryService {

    List<Telemetry> findAll();

    Telemetry save(@NotNull TelemetryDto telemetry);

    List<Telemetry> findByDeviceIdOrderByCreatedAtDesc(@NotNull Long deviceId,
                                                       @NotNull Integer size);

}
